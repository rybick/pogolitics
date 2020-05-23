import java.net.URL
import javax.json.Json
import javax.json.JsonObject
import javax.json.JsonArray

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
    val fastMoves: Set<JsonObject> = movesData.map { it.asJsonObject().getJsonObject("quickMove") }.toSet()
    val chargedMoves: Set<JsonObject> = movesData.map { it.asJsonObject().getJsonObject("chargeMove") }.toSet()
    val moves = Json.createObjectBuilder()
        .add("quick", toJsonArray(fastMoves))
        .add("charged", toJsonArray(chargedMoves))
        .build()
    return Json.createObjectBuilder(pokemonData)
        .add("moves", moves)
        .build()
}

fun parseJsonArray(s: String): JsonArray = Json.createReader(s.reader()).use { it.readArray() }
fun parseJsonObject(s: String): JsonObject = Json.createReader(s.reader()).use { it.readObject() }

fun toJsonArray(list: Collection<JsonObject>): JsonArray {
    val x = Json.createArrayBuilder()
    list.forEach { x.add(it) }
    return x.build()
}