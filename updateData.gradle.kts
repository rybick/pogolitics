import java.net.URL
import javax.json.Json
import javax.json.JsonObject
import javax.json.JsonArray
import javax.json.JsonValue
import java.lang.Exception

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        "classpath"(group = "javax.json", name = "javax.json-api", version = "1.1.4")
        "classpath"(group = "org.glassfish", name = "javax.json", version = "1.1.4")
    }
}

/*tasks.register("updatePokemonData") {
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
}*/

tasks.register("updateData") {
    doLast {
        updateData()
    }
}

fun updateData() {
    val gameData = URL("https://raw.githubusercontent.com/PokeMiners/game_masters/master/latest/latest.json")
        .readText()
        .let(::parseJsonArray)
    val pokemonData =
        gameData.filter { getData(it)["pokemonSettings"] != null }
    val (fastPveAttacks, chargedPveAttacks) = gameData
        .filter { getData(it)["moveSettings"] != null }
        .partitionLogged(::isFastAttack)
    val (fastPvpAttacks, chargedPvpAttacks) = gameData
        .filter { getData(it)["combatMove"] != null }
        .partitionLogged(::isFastAttack)
    combineAndConvertFastMovesData(fastPveAttacks, fastPvpAttacks)
        .also {
            File("./src/main/resources/data/attacks/fast.json").writeText(it.toString())
        }
    combineFastAndChargedAttacks(chargedPveAttacks, chargedPvpAttacks)
        .also {
            File("./src/main/resources/data/attacks/charged.json").writeText(it.toString())
        }
}

fun getData(element: JsonValue): JsonObject = element.asJsonObject().getJsonObject("data")!!

fun isFastAttack(element: JsonValue) =
    // it may mismatch some attacks like V0232_MOVE_WATER_GUN_FAST_BLASTOISE,
    // but it is not currently used and it's best we can do for now
    "_FAST$".toRegex()
        .containsMatchIn(element.asJsonObject().getString("templateId"))

/*
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
 */

fun combineAndConvertFastMovesData(fastPveAttacks: Collection<JsonValue>, fastPvpAttacks:  Collection<JsonValue>): List<JsonObject> {
    assert(fastPveAttacks.size == fastPvpAttacks.size)
    val fastPveAttacksById: Map<String, JsonObject> = fastPveAttacks
        .map { getData(it) }
        .associate { it.getString("templateId") to it.getJsonObject("moveSettings") }
    val fastPvpAttacksById: Map<String, JsonObject> = fastPvpAttacks
        .map { getData(it) }
        .associate { it.getString("templateId") to it.getJsonObject("combatMove") }
    return fastPveAttacksById.keys
        .map {
            val pve = fastPveAttacksById[it] ?: throw Exception("Missing fast PvE attack for id: $it")
            val pvp = fastPvpAttacksById["COMBAT_$it"] ?: throw Exception("Missing fast PvP attack for id: $it")
            convertFastMoveData(pve, pvp)
        }
    /* TODO later anything like this needed?
            .filter {
            (!it.isNull("pvpPower") && !it.isNull("pvpEnergy"))
                .apply { if (!this) logger.warn("Skipping charged move: " + it.getString("name")) }
        }
     */
}

fun convertFastMoveData(pve: JsonObject, pvp: JsonObject): JsonObject =
    try {
        json(
            "name" to convertToPrettyNameForFastAttack(pve.getString("vfxName")),
            "type" to convertType(pve.getString("pokemonType")),
            "pvp" to json(
                "power" to (pvp.getIntOrNull("power") ?: 0),
                "energy" to (pvp.getIntOrNull("energyDelta") ?: 0),
                // raw data has duration null if it lasts one turn, 1 if it lasts 2 turns and so on
                // since in the previous source (go hub), the duration was simply measured in turns
                // we recalculate it at this moment instead of production code
                "duration" to (pvp.getIntOrNull("durationTurns") ?: 0) + 1
            ),
            "pve" to json(
                "power" to (pve.getIntOrNull("power") ?: 0),
                "energy" to (pve.getIntOrNull("energyDelta") ?: 0),
                "duration" to pve.getInt("durationMs")
            )
        )
    } catch (e: Exception) {
        throw Exception("Error for pve = $pve; pvp = $pvp", e)
    }

fun convertToPrettyNameForFastAttack(vfxName: String): String {
    val suffix = "_fast"
    return if (vfxName.endsWith("_fast")) {
        vfxName.dropLast(suffix.length)
            .split("_")
            .joinToString(" ") { it.toLowerCase().capitalize() }
    } else throw Exception("Misformatted vfxName of fast attack: $vfxName")
}

// TODO later this could be commonized with fast attacks
fun combineAndConvertChargedMovesData(chargedPveAttacks: Collection<JsonValue>, chargedPvpAttacks:  Collection<JsonValue>): List<JsonObject> {
    assert(chargedPveAttacks.size == chargedPvpAttacks.size)
    val fastPveAttacksById: Map<String, JsonObject> = chargedPveAttacks
        .map { getData(it) }
        .associate { it.getString("templateId") to it.getJsonObject("moveSettings") }
    val fastPvpAttacksById: Map<String, JsonObject> = chargedPvpAttacks
        .map { getData(it) }
        .associate { it.getString("templateId") to it.getJsonObject("combatMove") }
    return fastPveAttacksById.keys
        .map {
            try {
                val pve = fastPveAttacksById[it] ?: throw Exception("Missing charged PvE attack for id: $it")
                val pvp = fastPvpAttacksById["COMBAT_$it"] ?: throw Exception("Missing charged PvP attack for id: $it")
                convertChargedMoveData(pve, pvp)
            } catch (e: Exception) {
                logger.warn("Skipping charged move $it due to: $e")
                null
            }
        }
        .filterNotNull()
}

fun convertChargedMoveData(pve: JsonObject, pvp: JsonObject): JsonObject =
    try {
        json(
            "name" to convertToPrettyNameForChargedAttack(pve.getString("vfxName")),
            "type" to convertType(pve.getString("pokemonType")),
            "pvp" to json(
                "power" to (pvp.getIntOrNull("power") ?: 0),
                "energy" to -(pvp.getIntOrNull("energyDelta") ?: 0)
            ),
            "pve" to json(
                "power" to (pve.getIntOrNull("power") ?: 0),
                "energy" to -(pve.getIntOrNull("energyDelta") ?: 0),
                "duration" to pve.getInt("durationMs")
            )
        )
    } catch (e: Exception) {
        throw Exception("Error for pve = $pve; pvp = $pvp", e)
    }

fun convertToPrettyNameForChargedAttack(vfxName: String): String =
    vfxName
        .split("_")
        .joinToString(" ") { it.toLowerCase().capitalize() }

fun convertType(typeString: String): String {
    val typePrefix = "POKEMON_TYPE"
    return if (typeString.startsWith("POKEMON_TYPE")) {
        typeString.substring(typePrefix.length + 1).toLowerCase()
    } else throw Exception("Misformatted type: $typeString")
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

fun JsonObject.getIntOrNull(key: String): Int? = getJsonNumber(key)?.intValue()

inline fun <T> Iterable<T>.partitionLogged(predicate: (T) -> Boolean): Pair<List<T>, List<T>> =
    partition {
        try {
            predicate(it)
        } catch (e: Exception) {
            throw Exception("Exception when handling $it", e)
        }
    }