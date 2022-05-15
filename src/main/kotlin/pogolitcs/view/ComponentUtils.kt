package pogolitcs.view

import pogolitcs.applicationRoot
import pogolitcs.model.PokemonType

fun iconPath(type: PokemonType): String {
    return path("/img/icon/${type.displayName}.png")
}

fun pokemonPagePath(pokedexNumber: Int, form: String? = null): String {
    return path("/#/pokemon/${pokedexNumber}" + (form?.let { "?form=$it" } ?: ""))
}

private fun path(url: String): String = applicationRoot + url