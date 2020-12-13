import java.net.URL
import javax.json.Json
import javax.json.JsonObject
import javax.json.JsonArray
import javax.json.JsonValue

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        "classpath"(group = "javax.json", name = "javax.json-api", version = "1.1.4")
        "classpath"(group = "org.glassfish", name = "javax.json", version = "1.1.4")
    }
}

tasks.register("updatePokemonData") {
    doLast {
        listOf(
            (1..865).map { "$it" to "$it" },
            listOf(
               "83?form=Galarian" to "83a",
               "105?form=Alola" to "105a",
               "150?form=Armored" to "150a",
               "487?form=Origin" to "487a"
            )
        ).flatten().forEach { downloadPokemonData(it.first, it.second) }
    }
}

tasks.register("updateMovesData") {
    doLast {
        downloadAttackData()
    }
}

tasks.register("updateData") {
    dependsOn("updatePokemonData")
    dependsOn("updateMovesData")
}

fun downloadPokemonData(source: String, target: String) {
    val pokemonData = URL("https://db.pokemongohub.net/api/pokemon/$source").readText().let(::parseNullableJsonObject)
            ?: return
    val movesData = URL("https://db.pokemongohub.net/api/movesets/with-pokemon/$source").readText().let(::parseJsonArray)
    File("./src/main/resources/data/pokemon/$target.json")
        .writeText(combinePokemonData(pokemonData, movesData).toString())
}

fun downloadAttackData() {
    downloadAttackData(
        sourceUrl = URL("https://db.pokemongohub.net/api/moves/with-filter/fast/with-stats"),
        targetFile = File("./src/main/resources/data/attacks/fast.json"),
        converter = ::convertFastMoveData
    )
    downloadAttackData(
        sourceUrl = URL("https://db.pokemongohub.net/api/moves/with-filter/charge/with-stats"),
        targetFile = File("./src/main/resources/data/attacks/charged.json"),
        converter = ::convertChargedMoveData
    )
}

fun downloadAttackData(sourceUrl: URL, targetFile: File, converter: (JsonArray) -> List<JsonObject>) {
    sourceUrl.readText()
        .let(::parseJsonArray)
        .let(converter)
        .also { targetFile.writeText(it.toString()) }
}

fun convertFastMoveData(attackData: JsonArray): List<JsonObject> {
    return attackData
        .map { it.asJsonObject() }
        .filter {
            (!it.isNull("pvpPower") && !it.isNull("pvpEnergy"))
                .apply { if (!this) logger.warn("Skipping charged move: " + it.getString("name")) }
        }
        .map { json(
            "name" to it.getString("name"),
            "type" to it.getString("type"),
            "pvp" to json(
                "power" to it.getInt("pvpPower"),
                "energy" to it.getInt("pvpEnergy"),
                "duration" to it.getInt("pvpDuration")
            ),
            "pve" to json(
                "power" to it.getInt("power"),
                "energy" to it.getInt("energy"),
                "duration" to it.getInt("duration")
            )
        ) }
}

fun convertChargedMoveData(attackData: JsonArray): List<JsonObject> {
    return attackData
        .map { it.asJsonObject() }
        .filter {
            (!it.isNull("pvpPower") && !it.isNull("pvpEnergy"))
                .apply { if (!this) logger.warn("Skipping charged move: " + it.getString("name")) }
        }
        .map { json(
            "name" to it.getString("name"),
            "type" to it.getString("type"),
            "pvp" to json(
                "power" to it.getInt("pvpPower"),
                "energy" to it.getInt("pvpEnergy")
            ),
            "pve" to json(
                "power" to it.getInt("power"),
                "energy" to it.getInt("energy"),
                "duration" to it.getInt("duration")
            )
        ) }
}

fun combinePokemonData(pokemonData: JsonObject, movesData: JsonArray): JsonObject {
    val quickMoves: Set<JsonObject> = movesData.map { it.asJsonObject().getJsonObject("quickMove") }.toSet()
    val chargedMoves: Set<JsonObject> = movesData.map { it.asJsonObject().getJsonObject("chargeMove") }.toSet()
    val pokemon = pokemonData
    return json(
        "id" to pokemon.getInt("id"),
        "name" to pokemon.getString("name"),
        "baseAttack" to pokemon.getInt("atk"),
        "baseDefense" to pokemon.getInt("def"),
        "baseStamina" to pokemon.getInt("sta"),
        "types" to json("primary" to pokemon.getString("type1"), "secondary" to pokemon.getString("type2", null)),
        "moves" to json(
            "quick" to toJsonArray(quickMoves.map(::mapMove)),
            "charged" to toJsonArray(chargedMoves.map(::mapMove))
        )
    )
}

fun mapMove(move: JsonObject): JsonObject {
    return json(
        "id" to move.getInt("id"),
        "name" to move.getString("name"),
        "type" to move.getString("type"),
        "legacy" to (move.getInt("isLegacy") != 0),
        "exclusive" to (move.getInt("isExclusive") != 0)
    )
}

fun json(vararg pairs: Pair<String, Any?>): JsonObject {
    val builder = Json.createObjectBuilder()
    pairs.forEach {
        when (it.second) {
            is JsonValue -> builder.add(it.first, it.second as JsonValue)
            is String -> builder.add(it.first, it.second as String)
            is BigInteger -> builder.add(it.first, it.second as BigInteger)
            is BigDecimal -> builder.add(it.first, it.second as BigDecimal)
            is Int -> builder.add(it.first, it.second as Int)
            is Long -> builder.add(it.first, it.second as Long)
            is Double -> builder.add(it.first, it.second as Double)
            is Boolean -> builder.add(it.first, it.second as Boolean)
            null -> builder.addNull(it.first)
            else -> throw Exception("Unexpected type of value: $it")
        }
    }
    return builder.build()
}

fun json(): JsonObject = JsonValue.EMPTY_JSON_OBJECT

fun parseJsonArray(s: String): JsonArray = Json.createReader(s.reader()).use { it.readArray() }

fun parseNullableJsonObject(s: String): JsonObject? {
    return Json.createReader(s.reader()).use {
        val value = it.readValue()
        when (value.valueType) {
            JsonValue.ValueType.OBJECT -> value.asJsonObject()
            JsonValue.ValueType.NULL -> null
            else -> throw Exception("Expected json object but got: $s")
        }
    }
}

fun toJsonArray(list: Collection<JsonValue>): JsonArray {
    val x = Json.createArrayBuilder()
    list.forEach { x.add(it) }
    return x.build()
}
