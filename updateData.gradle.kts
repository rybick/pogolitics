import javax.json.JsonObject
import javax.json.JsonArray

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        //"classpath"(group = "org.json", name = "json", version = "20190722")
        "classpath"(group = "javax.json", name = "javax.json-api", version = "1.1.4")
        "classpath"(group = "org.glassfish", name = "javax.json", version = "1.1.4")
        //"runtime"(group = "org.jetbrains.kotlinx", name = "kotlinx-serialization-runtime", version = "0.20.0-1.4-M1-release-99")
    }
}

tasks.register("updateData") {
    doLast {
        val s = "[{\"quickMove\":{\"id\":226,\"name\":\"Psycho Cut\",\"type\":\"psychic\",\"isLegacy\":0,\"isExclusive\":0},\"chargeMove\":{\"id\":24,\"name\":\"Flamethrower\",\"type\":\"fire\",\"isLegacy\":0,\"isExclusive\":0},\"isQuickMoveBoostedByWeather\":false,\"isChargeMoveBoostedByWeather\":false,\"weaveDPS\":15.943312666076174,\"tdo\":712.4747564216121,\"timeToFirstActivation\":4200},{\"quickMove\":{\"id\":226,\"name\":\"Psycho Cut\",\"type\":\"psychic\",\"isLegacy\":0,\"isExclusive\":0},\"chargeMove\":{\"id\":39,\"name\":\"Ice Beam\",\"type\":\"ice\",\"isLegacy\":0,\"isExclusive\":0},\"isQuickMoveBoostedByWeather\":false,\"isChargeMoveBoostedByWeather\":false,\"weaveDPS\":15.570934256055363,\"tdo\":695.833910034602,\"timeToFirstActivation\":4200},{\"quickMove\":{\"id\":226,\"name\":\"Psycho Cut\",\"type\":\"psychic\",\"isLegacy\":0,\"isExclusive\":0},\"chargeMove\":{\"id\":70,\"name\":\"Shadow Ball\",\"type\":\"ghost\",\"isLegacy\":1,\"isExclusive\":0},\"isQuickMoveBoostedByWeather\":false,\"isChargeMoveBoostedByWeather\":false,\"weaveDPS\":17.241379310344826,\"tdo\":770.4827586206897,\"timeToFirstActivation\":4200},{\"quickMove\":{\"id\":226,\"name\":\"Psycho Cut\",\"type\":\"psychic\",\"isLegacy\":0,\"isExclusive\":0},\"chargeMove\":{\"id\":79,\"name\":\"Thunderbolt\",\"type\":\"electric\",\"isLegacy\":0,\"isExclusive\":0},\"isQuickMoveBoostedByWeather\":false,\"isChargeMoveBoostedByWeather\":false,\"weaveDPS\":15.34526854219949,\"tdo\":685.7493606138108,\"timeToFirstActivation\":4200},{\"quickMove\":{\"id\":226,\"name\":\"Psycho Cut\",\"type\":\"psychic\",\"isLegacy\":0,\"isExclusive\":0},\"chargeMove\":{\"id\":108,\"name\":\"Psychic\",\"type\":\"psychic\",\"isLegacy\":0,\"isExclusive\":0},\"isQuickMoveBoostedByWeather\":false,\"isChargeMoveBoostedByWeather\":false,\"weaveDPS\":17.324350336862366,\"tdo\":774.1905678537055,\"timeToFirstActivation\":4200},{\"quickMove\":{\"id\":226,\"name\":\"Psycho Cut\",\"type\":\"psychic\",\"isLegacy\":0,\"isExclusive\":0},\"chargeMove\":{\"id\":109,\"name\":\"Psystrike\",\"type\":\"psychic\",\"isLegacy\":1,\"isExclusive\":1},\"isQuickMoveBoostedByWeather\":false,\"isChargeMoveBoostedByWeather\":false,\"weaveDPS\":19.29260450160772,\"tdo\":862.1479099678457,\"timeToFirstActivation\":4200},{\"quickMove\":{\"id\":226,\"name\":\"Psycho Cut\",\"type\":\"psychic\",\"isLegacy\":0,\"isExclusive\":0},\"chargeMove\":{\"id\":247,\"name\":\"Focus Blast\",\"type\":\"fighting\",\"isLegacy\":0,\"isExclusive\":0},\"isQuickMoveBoostedByWeather\":false,\"isChargeMoveBoostedByWeather\":false,\"weaveDPS\":14.778325123152708,\"tdo\":660.4137931034483,\"timeToFirstActivation\":7800},{\"quickMove\":{\"id\":235,\"name\":\"Confusion\",\"type\":\"psychic\",\"isLegacy\":0,\"isExclusive\":0},\"chargeMove\":{\"id\":24,\"name\":\"Flamethrower\",\"type\":\"fire\",\"isLegacy\":0,\"isExclusive\":0},\"isQuickMoveBoostedByWeather\":false,\"isChargeMoveBoostedByWeather\":false,\"weaveDPS\":16.74418604651163,\"tdo\":748.2641860465118,\"timeToFirstActivation\":6400},{\"quickMove\":{\"id\":235,\"name\":\"Confusion\",\"type\":\"psychic\",\"isLegacy\":0,\"isExclusive\":0},\"chargeMove\":{\"id\":39,\"name\":\"Ice Beam\",\"type\":\"ice\",\"isLegacy\":0,\"isExclusive\":0},\"isQuickMoveBoostedByWeather\":false,\"isChargeMoveBoostedByWeather\":false,\"weaveDPS\":15.557476231633535,\"tdo\":695.2324978392394,\"timeToFirstActivation\":6400},{\"quickMove\":{\"id\":235,\"name\":\"Confusion\",\"type\":\"psychic\",\"isLegacy\":0,\"isExclusive\":0},\"chargeMove\":{\"id\":70,\"name\":\"Shadow Ball\",\"type\":\"ghost\",\"isLegacy\":1,\"isExclusive\":0},\"isQuickMoveBoostedByWeather\":false,\"isChargeMoveBoostedByWeather\":false,\"weaveDPS\":17.408123791102515,\"tdo\":777.9342359767893,\"timeToFirstActivation\":6400},{\"quickMove\":{\"id\":235,\"name\":\"Confusion\",\"type\":\"psychic\",\"isLegacy\":0,\"isExclusive\":0},\"chargeMove\":{\"id\":79,\"name\":\"Thunderbolt\",\"type\":\"electric\",\"isLegacy\":0,\"isExclusive\":0},\"isQuickMoveBoostedByWeather\":false,\"isChargeMoveBoostedByWeather\":false,\"weaveDPS\":16.589861751152075,\"tdo\":741.3677419354839,\"timeToFirstActivation\":6400},{\"quickMove\":{\"id\":235,\"name\":\"Confusion\",\"type\":\"psychic\",\"isLegacy\":0,\"isExclusive\":0},\"chargeMove\":{\"id\":108,\"name\":\"Psychic\",\"type\":\"psychic\",\"isLegacy\":0,\"isExclusive\":0},\"isQuickMoveBoostedByWeather\":false,\"isChargeMoveBoostedByWeather\":false,\"weaveDPS\":17.30769230769231,\"tdo\":773.446153846154,\"timeToFirstActivation\":6400},{\"quickMove\":{\"id\":235,\"name\":\"Confusion\",\"type\":\"psychic\",\"isLegacy\":0,\"isExclusive\":0},\"chargeMove\":{\"id\":109,\"name\":\"Psystrike\",\"type\":\"psychic\",\"isLegacy\":1,\"isExclusive\":1},\"isQuickMoveBoostedByWeather\":false,\"isChargeMoveBoostedByWeather\":false,\"weaveDPS\":20.761245674740483,\"tdo\":927.7785467128026,\"timeToFirstActivation\":6400},{\"quickMove\":{\"id\":235,\"name\":\"Confusion\",\"type\":\"psychic\",\"isLegacy\":0,\"isExclusive\":0},\"chargeMove\":{\"id\":247,\"name\":\"Focus Blast\",\"type\":\"fighting\",\"isLegacy\":0,\"isExclusive\":0},\"isQuickMoveBoostedByWeather\":false,\"isChargeMoveBoostedByWeather\":false,\"weaveDPS\":16.775396085740912,\"tdo\":749.65890027959,\"timeToFirstActivation\":11200}]"
        val o = parseJsonArray(s)
        val fastAttacks: Set<JsonObject> = o.map { it.asJsonObject().getJsonObject("quickMove") }.toSet()
        val chargedAttacks: Set<JsonObject> = o.map { it.asJsonObject().getJsonObject("chargeMove") }.toSet()
        val result = javax.json.Json.createObjectBuilder()
            .add("quickMoves", toJsonArray(fastAttacks))
            .add("chargeMoves", toJsonArray(chargedAttacks))
            .build()
        println(": " + result)
    }
}

fun parseJsonArray(s: String): JsonArray {
    return javax.json.Json.createReader(s.reader()).use {
        it.readArray()
    }
}

fun toJsonArray(list: Collection<JsonObject>): JsonArray {
    val x = javax.json.Json.createArrayBuilder()
    list.forEach {
        x.add(it)
    }
    return x.build()
}