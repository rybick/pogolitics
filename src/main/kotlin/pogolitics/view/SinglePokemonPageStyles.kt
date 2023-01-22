package pogolitics.view

import csstype.Display
import csstype.Float
import csstype.TextAlign
import csstype.number
import csstype.pct
import emotion.css.ClassName

internal object SinglePokemonPageStyles {
    const val smallScreenMediaQuery = "screen and (max-width: 700px)"

    val headerWrapper = ClassName {
        paddingTop = StyleConstants.Padding.big
        paddingLeft = StyleConstants.Padding.medium
        paddingRight = StyleConstants.Padding.medium
        paddingBottom = StyleConstants.Padding.medium
        display = Display.flex
        fontSize = 160.pct
    }

    val spacer = ClassName {
        flexGrow = number(1.0)
        textAlign = TextAlign.center
    }

    val leftWrapper = ClassName {
        width = 50.pct
        float = Float.left
        "@media $smallScreenMediaQuery" {
            width = 100.pct
        }
    }

    val rightWrapper = ClassName {
        width = 50.pct
        float = Float.right
        "@media $smallScreenMediaQuery" {
            width = 100.pct
            float = Float.left
        }
    }
}
