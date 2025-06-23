import java.net.URL
import javax.json.Json
import javax.json.JsonObject
import javax.json.JsonArray
import javax.json.JsonValue
import javax.json.JsonString
import java.lang.Exception
import java.util.concurrent.TimeUnit

// TODO try moving the classes to a separate file[s] for better readability
// -- final classes (ones that are used in final results)

data class PokemonDto(
    val uniqueId: String,
    val pokedexNumber: Int,
    val name: String,
    val form: String?,
    val baseAttack: Int,
    val baseDefense: Int,
    val baseStamina: Int,
    val types: TypesDto,
    val moves: MovesDto
) {
    fun toJson() = json(
        "uniqueId" to uniqueId,
        "pokedexNumber" to pokedexNumber,
        "name" to name,
        "form" to form,
        "baseAttack" to baseAttack,
        "baseDefense" to baseDefense,
        "baseStamina" to baseStamina,
        "types" to types.toJson(),
        "moves" to moves.toJson()
    )
}

data class TypesDto(
    val primary: String,
    val secondary: String?
) {
    fun toJson() = json(
        "primary" to primary,
        "secondary" to secondary
    )
}

data class MovesDto(
    val quick: List<MoveDto>,
    val charged: List<MoveDto>
) {
    fun toJson() = json(
        "quick" to toJsonArray(quick.map { it.toJson() }),
        "charged" to toJsonArray(charged.map { it.toJson() })
    )
}

data class MoveDto(
    val id: String,
    val elite: Boolean
) {
    fun toJson() = json(
        "id" to id,
        "elite" to elite
    )
}

// intermidiate classes (that are only used as an intermidiate form)

data class FormsData(
    val pokemonNameCode: String,
    val forms: List<Form>
) {
    fun findFormAndIndex(rawFormNameCode: String?): Pair<Form?, Int?> =
        if (rawFormNameCode == null) {
            if (forms.isEmpty()) Pair(null, 0) else Pair(null, null)
        } else {
            val index = forms.indexOfFirst { it.id == rawFormNameCode }
            if (index != -1) {
                Pair(forms[index], index)
            } else {
                Pair(null, null)
            }
        }
}

data class Form(val id: String, val assetBundleSuffix: String?, val costume: Boolean)

// ----

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        "classpath"(group = "javax.json", name = "javax.json-api", version = "1.1.4")
        "classpath"(group = "org.glassfish", name = "javax.json", version = "1.1.4")
    }
}

tasks.register("updateData") {
    doLast {
        updateData()
        updateVersion()
    }
}

fun updateVersion() {
    logger.info("Started updating version...")
    if (gitHasChanges()) {
        val unixTime = System.currentTimeMillis().toString(32)
        File("./src/main/resources/resourcesVersion.js")
            .writeText("""resourcesVersion = "$unixTime" """)
    }
    logger.info("Finished updating version...")
}

fun gitHasChanges(): Boolean {
    val process: Process = ProcessBuilder("git", "status", "--porcelain", "src/main/resources/data/").start()
    val exitedGracefully = process.waitFor(10, TimeUnit.SECONDS)
    val output = String(process.inputStream.readBytes())
    if (!exitedGracefully) {
        logger.warn("git status timed out. Output is ${output.length} characters long.")
    }
    return !output.isEmpty()
}

fun updateData() {
    logger.info("Started updating data...")
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
    val forms = gameData
        .filter { getData(it)["formSettings"] != null }
    createPokemonIndex(pokemonData, convertToFormsDataByNameCode(forms))
        .also {
            File("./src/main/resources/data/pokemon/index.json").writeText(it.toString())
        }
    convertAllPokemonData(
        pokemonData = pokemonData,
        overrides = File("./src/main/resources/data/overrides.json").readText().let(::parseJsonObject)
    )
        .forEach {
            val uniqueId = it.getAsString("uniqueId")
            File("./src/main/resources/data/pokemon/${uniqueId}.json").writeText(it.toString())
        }
    combineAndConvertFastMovesData(fastPveAttacks, fastPvpAttacks)
        .also {
            File("./src/main/resources/data/attacks/fast.json").writeText(it.toString())
        }
    combineAndConvertChargedMovesData(chargedPveAttacks, chargedPvpAttacks)
        .also {
            File("./src/main/resources/data/attacks/charged.json").writeText(it.toString())
        }
    logger.info("Finished updating data.")
}

fun createPokemonIndex(pokemonData: List<JsonValue>, formsDataByNameCode: Map<String, FormsData>): JsonArray =
    pokemonData
        .map(::getData)
        .map {
            convertToPokemonIndexEntry(
                templateId = it.getString("templateId"),
                pokemonSettings = it.getJsonObject("pokemonSettings"),
                formsDataByNameCode = formsDataByNameCode
            )
        }
        .let(::toJsonArray)

fun convertToFormsDataByNameCode(forms: List<JsonValue>): Map<String, FormsData> =
    forms
        .map(::getData)
        .map { it.getJsonObject("formSettings") }
        .map { convertToFormsData(it) }
        .associateBy { it.pokemonNameCode }

fun convertAllPokemonData(pokemonData: List<JsonValue>, overrides: JsonObject): Collection<JsonObject> =
    pokemonData
        .map(::getData)
        .map {
            val templateId = it.getString("templateId")
            val overridesForPokemon = overrides.getJsonObject(templateId)
            //convertPokemonMegaForms()
            convertPokemonData(templateId, it.getJsonObject("pokemonSettings"), overridesForPokemon)
        }
        .map { it.toJson() }

fun getData(element: JsonValue): JsonObject = element.asJsonObject().getJsonObject("data")!!

fun isFastAttack(element: JsonValue) =
    // it may mismatch some attacks like V0232_MOVE_WATER_GUN_FAST_BLASTOISE,
    // but it is not currently used and it's best we can do for now
    "_FAST$".toRegex()
        .containsMatchIn(element.asJsonObject().getString("templateId"))

fun combineAndConvertFastMovesData(
    fastPveAttacks: Collection<JsonValue>,
    fastPvpAttacks: Collection<JsonValue>
): JsonArray =
    matchPveAndPvpMoves(fastPveAttacks, fastPvpAttacks, "fast")
        .map { (pve, pvp) -> convertFastMoveData(pve, pvp) }
        .let(::toJsonArray)

fun combineAndConvertChargedMovesData(
    chargedPveAttacks: Collection<JsonValue>,
    chargedPvpAttacks: Collection<JsonValue>
): JsonArray =
    matchPveAndPvpMoves(chargedPveAttacks, chargedPvpAttacks, "charged")
        .map { (pve, pvp) -> convertChargedMoveData(pve, pvp) }
        .let { it + THUNDER_CAGE() } // TMP override
        .let(::toJsonArray)

fun matchPveAndPvpMoves(
    pveAttacks: Collection<JsonValue>,
    pvpAttacks: Collection<JsonValue>,
    moveType: String
): List<Pair<JsonObject, JsonObject>> {
    val pveAttacksById: Map<String, JsonObject> = pveAttacks
        .map { getData(it).getJsonObject("moveSettings") }
        .associateBy { it.getAsString("movementId") } // "movementId" can be both String or Int
    val pvpAttacksById: Map<String, JsonObject> = pvpAttacks
        .map { getData(it).getJsonObject("combatMove") }
        .associateBy { it.getAsString("uniqueId") } // "uniqueId" can be both String or Int
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
            "id" to pve.getAsString("movementId"),
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
    } else {
        val prettyName = vfxName
            .split("_")
            .joinToString(" ") { it.toLowerCase().capitalize() }
        logger.warn(
            """Stumbled upon non-standard naming of a fast attack: '$vfxName' - converting it to: $prettyName"""
        );
        prettyName
    }
}

fun convertChargedMoveData(pve: JsonObject, pvp: JsonObject): JsonObject =
    try {
        json(
            "id" to pve.getAsString("movementId"),
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

fun convertToPokemonIndexEntry(
    templateId: String,
    pokemonSettings: JsonObject,
    formsDataByNameCode: Map<String, FormsData>
): JsonObject =
    with(pokemonSettings) {
        val nameCode = getString("pokemonId")
        val formNameCode = getString("form", null)
        val formsData: FormsData = formsDataByNameCode[nameCode]!!
        val (form: Form?, formIndex: Int?) = formsData.findFormAndIndex(formNameCode)
        json(
            "uniqueId" to templateId,
            "pokedexNumber" to convertToPokedexNumber(templateId),
            "nameCode" to nameCode,
            "name" to convertToPrettyPokemonName(getString("pokemonId")),
            "form" to convertToPrettyForm(formNameCode, getString("pokemonId")),
            "formNameCode" to formNameCode,
            "formIndex" to formIndex,
            "formCostume" to form?.costume
        )
    }

fun convertToFormsData(formSettings: JsonObject): FormsData =
    with(formSettings) {
        FormsData(
            pokemonNameCode = getString("pokemon"),
            forms = (getJsonArray("forms") ?: toJsonArray(listOf()))
                .map { it.asJsonObject() }
                .mapNotNull {
                    val id = it.getString("form", null)
                    if (id != null) {
                        Form(
                            id = id,
                            assetBundleSuffix = it.getString("assetBundleSuffix", null),
                            costume = it.getBoolean("isCostume", false)
                        )
                    } else {
                        logger.info("Skipping malformed form ($it) from ${getString("pokemon")}.")
                        null
                    }
                }
        )
    }

fun convertPokemonData(templateId: String, pokemonSettings: JsonObject, overrides: JsonObject?): PokemonDto =
    with(pokemonSettings) {
        PokemonDto(
            uniqueId = templateId,
            pokedexNumber = convertToPokedexNumber(templateId),
            name = convertToPrettyPokemonName(getString("pokemonId")),
            form = convertToPrettyForm(getString("form", null), getString("pokemonId")),
            baseAttack = getJsonObject("stats").getInt("baseAttack", 0),
            baseDefense = getJsonObject("stats").getInt("baseDefense", 0),
            baseStamina = getJsonObject("stats").getInt("baseStamina", 0),
            types = TypesDto(
                primary = getString("type").let(::convertType),
                secondary = getString("type2", null)?.let(::convertType)
            ),
            moves = MovesDto(
                quick = mapMoves(
                    moves = getJsonArray("quickMoves"),
                    eliteMoves = getJsonArray("eliteQuickMove"),
                    overrides = overrides?.getJsonArray("quick"),
                    uniqueId = templateId
                ),
                charged = mapMoves(
                    moves = getJsonArray("cinematicMoves"),
                    eliteMoves = getJsonArray("eliteCinematicMove"),
                    overrides = overrides?.getJsonArray("charged"),
                    uniqueId = templateId
                )
            )
        )
    }


fun mapMoves(moves: JsonArray?, eliteMoves: JsonArray?, overrides: JsonArray?, uniqueId: String): List<MoveDto> {
    val minedAttacks: List<MoveDto> = (moves?.map(::mapMove) ?: emptyList()) +
        (eliteMoves?.map(::mapEliteMove) ?: emptyList())
    val minedAttacksSet = minedAttacks.toSet()
    val overridesAsList: List<String> = overrides?.map { it as JsonString }?.map { it.getString() } ?: emptyList()
    logDuplicates(minedAttacksSet, overridesAsList, uniqueId)
    return (minedAttacksSet + overridesAsList.map(::mapOverride)).toList()
}

fun logDuplicates(minedAttacks: Set<MoveDto>, overrides: List<String>, uniqueId: String) {
    val duplicatedOverrides = minedAttacks.map { it.id }.intersect(overrides)
    if (!duplicatedOverrides.isEmpty()) {
        logger.warn(
            "Unnecessary overrides for $uniqueId: $duplicatedOverrides." +
                    " Please remove them from data/overrides.json file!"
        )
    }
}

fun mapMove(moveId: JsonValue): MoveDto =
    MoveDto(
        id = moveId.asString(),
        elite = false
    )
fun mapEliteMove(moveId: JsonValue): MoveDto =
    MoveDto(
        id = moveId.asString(),
        elite = true
    )
fun mapOverride(moveId: String): MoveDto =
    MoveDto(
        id = moveId,
        elite = true
    )

fun convertToPokedexNumber(templateId: String): Int =
    if (templateId[0] == 'V' && templateId.substring(1, 5).matches("[0-9]*".toRegex())) {
        templateId.substring(1, 5).toInt()
    } else throw Exception("Misformatted pokemon templateId: $templateId")

fun convertToPrettyPokemonName(pokemonId: String): String =
    pokemonId
        .split('_')
        .joinToString(" ") {
            it.toLowerCase().capitalize()
        }

fun convertToPrettyForm(maybeForm: String?, pokemonId: String): String? =
    maybeForm?.let { form ->
        if (form.startsWith(pokemonId)) {
            form.substring(pokemonId.length + 1)
        } else {
            form
        }
    }

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

fun parseJsonArray(s: String): JsonArray = Json.createReader(s.reader()).use {
    try {
        val x = it
        val y = it.readArray()
        y
    } catch (e: Throwable) {
        e.printStackTrace();
        throw e;
    }
}

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

fun parseJsonObject(s: String): JsonObject = parseNullableJsonObject(s)!!

fun toJsonArray(list: Collection<JsonValue>): JsonArray {
    val x = Json.createArrayBuilder()
    list.forEach { x.add(it) }
    return x.build()
}

fun emptyJsonArray() = Json.createArrayBuilder().build()

// gets a value as String even if it's not a string
// useful for some fields that sometimes are strings and sometimes ints (for whatever reason)
fun JsonObject.getAsString(key: String): String {
    val type = get(key)?.getValueType();
    return when (type) {
        JsonValue.ValueType.STRING -> getString(key)
        JsonValue.ValueType.NUMBER -> getInt(key).toString()
        else -> throw Exception("Currently only STRING and NUMBER supported by getAsString but got $type")
    }
}

fun JsonValue.asString(): String = json("value" to this).getAsString("value")

inline fun <T> Iterable<T>.partitionLogged(predicate: (T) -> Boolean): Pair<List<T>, List<T>> =
    partition {
        try {
            predicate(it)
        } catch (e: Exception) {
            throw Exception("Exception when handling $it", e)
        }
    }


/// -- tmp overrides

fun THUNDER_CAGE() = json(
    "id" to "__THUNDER_CAGE",
    "name" to "Thunder Cage",
    "type" to "electric",
    "pvp" to json(
        "power" to 60,
        "energy" to 40
    ),
    "pve" to json(
        "power" to 220,
        "energy" to 100,
        "duration" to 3300
    )
)