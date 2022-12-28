package pogolitics.view

import org.w3c.dom.url.URL
import pogolitics.applicationRoot
import pogolitics.model.BattleMode
import pogolitics.model.PokemonType

fun iconPath(type: PokemonType): String =
    path("/img/icon/${type.displayName}.png")

fun pokemonPagePath(pokedexNumber: Int, form: String? = null, mode: BattleMode = BattleMode.default): String =
    path("/#/pokemon/${pokedexNumber}", mapOf("form" to form, "mode" to mode.toString()))

fun pokemonListPagePath(): String =
    path("#/pokemon")

private fun path(baseUrl: String, params: Map<String, String?> = mapOf()): String =
    applicationRoot + baseUrl +
        if (params.isEmpty()) {
            ""
        } else {
            "?" + params.entries
                .filter { it.value != null }
                .joinToString("&") { "${it.key}=${it.value}" }
        }