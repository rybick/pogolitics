import csstype.Display
import csstype.Margin
import csstype.TextAlign
import csstype.pct
import csstype.px
import emotion.css.ClassName
import pogolitics.view.StyleConstants

internal object BasicPokemonInfoStyles {
    val wrapper = ClassName {
        textAlign = TextAlign.center
    }

    val pokemonId = ClassName {
        color = StyleConstants.Colors.regular.secondaryText
        marginRight = StyleConstants.Margin.small
        fontSize = 80.pct
    }

    val pokemonForm = ClassName {
        display = Display.inlineBlock
        color = StyleConstants.Colors.regular.secondaryText
        marginLeft = StyleConstants.Margin.small
        fontSize = 80.pct
    }

    val staticStatsWrapper = ClassName {
        textAlign = TextAlign.center
    }

    val typesWrapper = ClassName {
        textAlign = TextAlign.center
        margin = StyleConstants.Margin.medium
        marginBottom = StyleConstants.Margin.semiBig
    }

    val pokemonPictureWrapper = ClassName {
        textAlign = TextAlign.center
    }

    val pokemonPictureImg = ClassName {
        margin = Margin(StyleConstants.Margin.medium, StyleConstants.Margin.medium, 0.px)
    }

    val typeIconWrapper = ClassName {
        margin = StyleConstants.Margin.small
    }
}