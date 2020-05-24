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

tasks.register("updateData") {
    doLast {
        val pokemonDataString = URL("https://db.pokemongohub.net/api/pokemon/150").readText()
        val movesDataString = URL("https://db.pokemongohub.net/api/movesets/with-pokemon/150").readText()
        val pokemonData = parseJsonObject(pokemonDataString)
        val movesData = parseJsonArray(movesDataString)
        File("./src/main/resources/data/pokemon/150.json")
            .writeText(combinePokemonData(pokemonData, movesData).toString())
    }
}

fun combinePokemonData(pokemonData: JsonObject, movesData: JsonArray): JsonObject {
    val quickMoves: Set<JsonObject> = movesData.map { it.asJsonObject().getJsonObject("quickMove") }.toSet()
    val chargedMoves: Set<JsonObject> = movesData.map { it.asJsonObject().getJsonObject("chargeMove") }.toSet()
    val pokemon = pokemonData
    return json(
        "id" to pokemon.getInt("id"),
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

fun json(vararg jsons: JsonValue): JsonArray = toJsonArray(jsons.toList())
fun parseJsonArray(s: String): JsonArray = Json.createReader(s.reader()).use { it.readArray() }

fun parseJsonObject(s: String): JsonObject = Json.createReader(s.reader()).use { it.readObject() }

fun toJsonArray(list: Collection<JsonValue>): JsonArray {
    val x = Json.createArrayBuilder()
    list.forEach { x.add(it) }
    return x.build()
}
