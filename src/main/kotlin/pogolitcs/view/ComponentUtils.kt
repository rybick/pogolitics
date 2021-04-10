package pogolitcs.view

import pogolitcs.applicationRoot
import pogolitcs.model.PokemonType

fun iconPath(type: PokemonType): String {
    return path("/img/icon/${type.displayName}.png")
}

fun pokemonPagePath(pokemonFamilyId: Int): String {
    return path("/#/pokemon/${pokemonFamilyId}")
}

private fun path(url: String): String = applicationRoot + url