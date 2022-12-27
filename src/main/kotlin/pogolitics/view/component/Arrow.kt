package pogolitics.view.component

import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import pogolitics.view.StyleConstants
import react.Props
import react.fc
import styled.*

val Arrow = fc<ArrowProps> { props ->
    styledA(href = props.href) {
        css { +props.direction.style }
        styledSpan {}
    }
}

interface ArrowProps: Props {
    var direction: ArrowDirection
    var href: String
}

enum class ArrowDirection(val style: RuleSet) {
    LEFT(Styles.arrowLeft), RIGHT(Styles.arrowRight);
}

private object Styles: StyleSheet("ArrowComponentStyles", isStatic = true) {
    val arrowLeft by Styles.css {
        +arrow
        span {
            marginLeft = -2.px
            after {
                content = QuotedString("⮜")
            }
        }
    }

    val arrowRight by Styles.css {
        +arrow
        span {
            marginLeft = 2.px
            after {
                content = QuotedString("⮞")
            }
        }
    }

    private val arrow by Styles.css {
        display = Display.inlineBlock
        cursor = Cursor.pointer
        width = 42.px
        height = 42.px
        textAlign = TextAlign.center
        borderRadius = 50.pct
        marginLeft = StyleConstants.Margin.small
        marginRight = StyleConstants.Margin.small
        hover {
            color = StyleConstants.Colors.primary.text
            backgroundColor = StyleConstants.Colors.primary.bg
            textDecoration = TextDecoration.none
        }
    }
}