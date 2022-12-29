package pogolitics.view.component

import csstype.*
import emotion.react.css
import pogolitics.CssBuilder
import pogolitics.cssClass
import pogolitics.plus
import pogolitics.view.StyleConstants
import react.Props
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.span
import react.fc

val NavigationArrow = fc<ArrowProps> { props ->
    a {
        attrs.href = props.href
        attrs.css(props.direction.style)
        span {}
    }
}

interface ArrowProps: Props {
    var direction: NavigationDirection
    var href: String
}

enum class NavigationDirection(val style: CssBuilder) {
    PREVIOUS(NavigationArrowStyles.arrowPrevious), NEXT(NavigationArrowStyles.arrowNext);
}

private object NavigationArrowStyles {
    private val arrow = cssClass {
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
            textDecoration = None.none
        }
    }

    val arrowPrevious = arrow + cssClass {
        span {
            marginLeft = (-3).px
            display = Display.block
            transform = scale(-1, 1)
            after {
                content = string("➤") // TODO later mig QuotedString?
            }
        }
    }

    val arrowNext = arrow + cssClass {
        span {
            marginLeft = 3.px
            display = Display.block
            after {
                content = string("➤")
            }
        }
    }
}