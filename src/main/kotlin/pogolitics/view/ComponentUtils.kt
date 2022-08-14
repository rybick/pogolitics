package pogolitics.view

import pogolitics.applicationRoot
import pogolitics.model.PokemonType

fun iconPath(type: PokemonType): String =
    path("/img/icon/${type.displayName}.png")

fun pokemonPagePath(pokedexNumber: Int, form: String? = null): String =
    path("/#/pokemon/${pokedexNumber}" + (form?.let { "?form=$it" } ?: ""))

fun pokemonListPagePath(): String =
    path("#/pokemon")

private fun path(url: String): String = applicationRoot + url