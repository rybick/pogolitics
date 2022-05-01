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
    filterAndConvertPokemonData(pokemonData)
        .take(3)
        .forEach {
            val id = it.getInt("id")
            File("./src/main/resources/data/pokemon/${id}TMP.json").writeText(it.toString())
        }
    combineAndConvertFastMovesData(fastPveAttacks, fastPvpAttacks)
        .also {
            File("./src/main/resources/data/attacks/fast.json").writeText(it.toString())
        }
    combineAndConvertChargedMovesData(chargedPveAttacks, chargedPvpAttacks)
        .also {
            File("./src/main/resources/data/attacks/charged.json").writeText(it.toString())
        }
}

fun filterAndConvertPokemonData(pokemonData: List<JsonValue>): List<JsonObject> =
    pokemonData
        .map(::getData)
        .groupBy { it.getJsonObject("pokemonSettings").getString("pokemonId") }
        .mapValues { (key, value) ->
            value
                .filter { it.getJsonObject("pokemonSettings")["form"] == null }
                .also {
                    if (it.size != 1) {
                        throw Exception("multiple pokemon with basic form for $key: ${it.map { it.getString("templateId") }}")
                    }
                }
                .first()
        }
        .values
        .map { combinePokemonData(it.getString("templateId"), it.getJsonObject("pokemonSettings"), emptyList()) }

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

fun combineAndConvertFastMovesData(
    fastPveAttacks: Collection<JsonValue>,
    fastPvpAttacks: Collection<JsonValue>
): List<JsonObject> =
    matchPveAndPvpMoves(fastPveAttacks, fastPvpAttacks, "fast")
        .map { (pve, pvp) -> convertFastMoveData(pve, pvp) }

fun combineAndConvertChargedMovesData(
    chargedPveAttacks: Collection<JsonValue>,
    chargedPvpAttacks: Collection<JsonValue>
): List<JsonObject> =
    matchPveAndPvpMoves(chargedPveAttacks, chargedPvpAttacks, "charged")
        .map { (pve, pvp) -> convertChargedMoveData(pve, pvp) }

fun matchPveAndPvpMoves(
    pveAttacks: Collection<JsonValue>,
    pvpAttacks: Collection<JsonValue>,
    moveType: String
): List<Pair<JsonObject, JsonObject>> {
    val pveAttacksById: Map<String, JsonObject> = pveAttacks
        .map { getData(it).getJsonObject("moveSettings") }
        .associateBy { it.getString("movementId") }
    val pvpAttacksById: Map<String, JsonObject> = pvpAttacks
        .map { getData(it).getJsonObject("combatMove") }
        .associateBy { it.getString("uniqueId") }
    with(pveAttacksById.keys - pvpAttacksById.keys) {
        if (isNotEmpty())
            logger.warn("Following $moveType PvE attacks do not have matching PvP attacks and will be skipped: $this")
    }
    with(pvpAttacksById.keys - pveAttacksById.keys) {
        if (isNotEmpty())
            logger.warn("Following $moveType PvP attacks do not have matching PvE attacks and will be skipped: $this")
    }
    return pveAttacksById.keys.intersect(pvpAttacksById.keys)
        .map { Pair(pveAttacksById[it]!!, pvpAttacksById[it]!!) }
}

fun convertFastMoveData(pve: JsonObject, pvp: JsonObject): JsonObject =
    try {
        json(
            "id" to pve.getString("movementId"),
            "name" to convertToPrettyNameForFastAttack(pve.getString("vfxName")),
            "type" to convertType(pve.getString("pokemonType")),
            "pvp" to json(
                "power" to pvp.getInt("power", 0),
                "energy" to pvp.getInt("energyDelta", 0),
                // raw data has duration null if it lasts one turn, 1 if it lasts 2 turns and so on
                // since in the previous source (go hub), the duration was simply measured in turns
                // we recalculate it at this moment instead of production code
                "duration" to pvp.getInt("durationTurns", 0) + 1
            ),
            "pve" to json(
                "power" to pve.getInt("power", 0),
                "energy" to pve.getInt("energyDelta", 0),
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

fun convertChargedMoveData(pve: JsonObject, pvp: JsonObject): JsonObject =
    try {
        json(
            "id" to pve.getString("movementId"),
            "name" to convertToPrettyNameForChargedAttack(pve.getString("vfxName")),
            "type" to convertType(pve.getString("pokemonType")),
            "pvp" to json(
                "power" to pvp.getInt("power", 0),
                "energy" to -pvp.getInt("energyDelta", 0)
            ),
            "pve" to json(
                "power" to pve.getInt("power", 0),
                "energy" to -pve.getInt("energyDelta", 0),
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

fun combinePokemonData(templateId: String, pokemonSettings: JsonObject, movesData: Collection<JsonObject>): JsonObject {
    println(templateId)
    val quickMoves: Set<JsonObject> = movesData.map { it.asJsonObject().getJsonObject("quickMove") }.toSet()
    val chargedMoves: Set<JsonObject> = movesData.map { it.asJsonObject().getJsonObject("chargeMove") }.toSet()
    return with(pokemonSettings) {
        json(
            "id" to convertToPokedexNumber(templateId),
            "name" to convertToPrettyPokemonName(getString("pokemonId")),
            "baseAttack" to getJsonObject("stats").getInt("baseAttack", 0),
            "baseDefense" to getJsonObject("stats").getInt("baseDefense", 0),
            "baseStamina" to getJsonObject("stats").getInt("baseStamina", 0),
            "types" to json(
                "primary" to getString("type").let(::convertType),
                "secondary" to getString("type2", null)?.let(::convertType)
            ),
            "moves" to json(
                "quick" to toJsonArray(quickMoves.map(::mapMove)),
                "charged" to toJsonArray(chargedMoves.map(::mapMove))
            )
        )
    }
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

fun convertToPokedexNumber(templateId: String): Int =
    if (templateId[0] == 'V' && templateId.substring(1, 5).matches("[0-9]*".toRegex())) {
        templateId.substring(1, 5).toInt()
    } else throw Exception("Misformatted pokemon templateId: $templateId")

fun convertToPrettyPokemonName(pokemonId: String): String = pokemonId.toLowerCase().capitalize()

fun convertType(typeString: String): String {
    val typePrefix = "POKEMON_TYPE"
    return if (typeString.startsWith("POKEMON_TYPE")) {
        typeString.substring(typePrefix.length + 1).toLowerCase()
    } else throw Exception("Misformatted type: $typeString")
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

inline fun <T> Iterable<T>.partitionLogged(predicate: (T) -> Boolean): Pair<List<T>, List<T>> =
    partition {
        try {
            predicate(it)
        } catch (e: Exception) {
            throw Exception("Exception when handling $it", e)
        }
    }