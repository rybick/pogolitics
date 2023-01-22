package pogolitics.view

import pogolitics.applicationRoot
import pogolitics.model.BattleMode
import pogolitics.model.PokemonForm
import pogolitics.model.PokemonType

fun iconPath(type: PokemonType): String =
    path("/img/icon/${type.displayName}.png")

fun logoPath(): String =
    path("/img/logo/120x50.png")

fun loadingImagePath(): String =
    path("/img/misc/loading.gif")

// TODO download with updateData task instead of hotlinking?
// example paths (they both result in the same):
// https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Pokemon/Addressable%20Assets/pm1.icon.png
// https://github.com/PokeMiners/pogo_assets/blob/master/Images/Pokemon/Addressable%20Assets/pm1.icon.png?raw=true
fun pokemonImagePath(pokedexNumber: Int, form: PokemonForm, shiny: Boolean): String {
    val baseUrl = "https://raw.githubusercontent.com/PokeMiners/pogo_assets/master/Images/Pokemon/Addressable%20Assets/"
    val shinySegment = if (shiny) ".s" else ""
    val formSegment = if (form.isDefault() || form.isNormal()) "" else ".f${form.code}"
    val fileName = "pm$pokedexNumber$formSegment$shinySegment.icon.png"
    return baseUrl + fileName
}

fun pokemonPagePath(pokedexNumber: Int, form: PokemonForm? = null, mode: BattleMode = BattleMode.default): String =
    pagePath(Page.POKEMON(pokedexNumber, form, mode))

fun pokemonListPagePath(): String = pagePath(Page.POKEMON_LIST)

fun pagePath(page: Page): String =
    path(
        baseUrl = "/" + page.getFullPath().joinToString("/") { it.pathSegment },
        params = page.urlParams
    )

sealed class Page(
    val parent: Page?,
    val prettyName: String,
    val pathSegment: String,
    val urlParams: Map<String, String?> = mapOf()
) {
    object HOME: Page(null, "Home", "#")
    object POKEMON_LIST: Page(HOME, "Pokemon", "pokemon")
    class POKEMON(pokedexNumber: Int, pokemonForm: PokemonForm?, mode: BattleMode, prettyName: String? = null):
        Page(
            parent = POKEMON_LIST,
            prettyName = prettyName ?: "$pokedexNumber",
            pathSegment = "$pokedexNumber",
            urlParams = mapOf("form" to pokemonForm?.code, "mode" to mode.toString())
        )

    fun getFullPath(): List<Page> {
        fun rawPagePath(page: Page?): List<Page> =
            if (page == null) {
                listOf()
            } else {
                rawPagePath(page.parent) + page
            }
        return rawPagePath(this)
    }
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