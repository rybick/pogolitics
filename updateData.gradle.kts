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
    return Json.createObjectBuilder(pokemonData)
        .add("moves", json("quick" to toJsonArray(quickMoves), "charged" to toJsonArray(chargedMoves)))
        .build()
}

fun json(vararg pairs: Pair<String, Any?>): JsonObject {
    val builder = Json.createObjectBuilder()
    pairs.forEach {
        when (it.second) {
            is JsonValue -> builder.add(it.first, it.second as JsonValue)
            is String -> builder.add(it.first, it.second as String)
            is BigInteger -> builder.add(it.first, it.second as String)
            is BigDecimal -> builder.add(it.first, it.second as String)
            is Int -> builder.add(it.first, it.second as String)
            is Long -> builder.add(it.first, it.second as String)
            is Double -> builder.add(it.first, it.second as String)
            is Boolean -> builder.add(it.first, it.second as String)
            null -> builder.addNull(it.first)
            else -> throw Exception("Unexpected type of value " + it)
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