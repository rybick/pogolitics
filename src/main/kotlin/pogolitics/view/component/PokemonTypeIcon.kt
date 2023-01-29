package pogolitics.view.component

import csstype.Length
import csstype.Margin
import csstype.rem
import emotion.react.css
import pogolitics.model.PokemonType
import pogolitics.view.StyleConstants
import pogolitics.view.iconPath
import react.FC
import react.Props
import react.dom.html.ReactHTML

val PokemonTypeIcon = FC<PokemonTypeIconProps> { props ->
    ReactHTML.img {
        src = iconPath(props.type)
        css {
            height = props.size.dimensions
            margin = Margin(StyleConstants.Margin.small, StyleConstants.Margin.small)
        }
    }
}

external interface PokemonTypeIconProps: Props {
    var type: PokemonType
    var size: PokemonTypeIconSize
}

enum class PokemonTypeIconSize(val dimensions: Length) {
    SMALL(1.5.rem),
    BIG(2.5.rem)
}