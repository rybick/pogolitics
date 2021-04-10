package pogolitcs.view

import pogolitcs.applicationRoot
import pogolitcs.model.PokemonType

fun iconPath(type: PokemonType): String {
    return applicationRoot + "/img/icon/${type.displayName}.png"
}