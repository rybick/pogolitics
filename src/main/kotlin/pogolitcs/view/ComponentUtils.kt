package pogolitcs.view

import pogolitcs.model.PokemonType

fun iconPath(type: PokemonType): String {
    return "/img/icon/${type.displayName}.png"
}