package pogolitics.view

import pogolitics.applicationRoot
import pogolitics.model.BattleMode
import pogolitics.model.PokemonForm
import pogolitics.model.PokemonType

fun iconPath(type: PokemonType): String =
    path("/img/icon/${type.displayName}.png")

fun logoPath(): String =
    path("/img/logo/120x70.png")

fun pokemonPagePath(pokedexNumber: Int, form: PokemonForm? = null, mode: BattleMode = BattleMode.default): String =
    pagePath(Page.POKEMON(pokedexNumber, form, mode))

fun pokemonListPagePath(): String = pagePath(Page.POKEMON_LIST)

fun pagePath(page: Page): String {
    fun rawPagePath(page: Page?): String =
        if (page == null) {
            ""
        } else {
            rawPagePath(page.parent) + "/" + page.pathSegment
        }
    return path(rawPagePath(page), page.urlParams)
}

sealed class Page(
    val parent: Page?,
    val prettyName: String,
    val pathSegment: String,
    val urlParams: Map<String, String?> = mapOf()
) {
    object HOME: Page(null, "⌂", "#")
    object POKEMON_LIST: Page(HOME, "pokemon list", "pokemon")
    class POKEMON(pokedexNumber: Int, pokemonForm: PokemonForm?, mode: BattleMode, prettyName: String? = null):
        Page(
            parent = POKEMON_LIST,
            prettyName = prettyName ?: "$pokedexNumber",
            pathSegment = "$pokedexNumber",
            urlParams = mapOf("form" to pokemonForm?.code, "mode" to mode.toString())
        )
}

private fun path(baseUrl: String, params: Map<String, String?> = mapOf()): String =
    applicationRoot + baseUrl +
        if (params.isEmpty()) {
            ""
        } else {
            "?" + params.entries
                .filter { it.value != null }
                .joinToString("&") { "${it.key}=${it.value}" }
        }