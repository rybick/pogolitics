package pogolitics.view.component

import csstype.ClassName
import csstype.Content
import csstype.Display
import csstype.None
import csstype.TextAlign
import csstype.pct
import csstype.px
import csstype.scale
import emotion.css.ClassName
import emotion.react.css
import pogolitics.pointer
import pogolitics.view.StyleConstants
import react.Props
import react.attrs
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.span
import react.fc

val NavigationArrow = fc<ArrowProps> { props ->
    a {
        attrs.href = props.href
        attrs.css(props.direction.style) {}
        span {}
    }
}

interface ArrowProps: Props {
    var direction: NavigationDirection
    var href: String
}

enum class NavigationDirection(val style: ClassName) {
    PREVIOUS(NavigationArrowStyles.arrowPrevious), NEXT(NavigationArrowStyles.arrowNext);
}

private object NavigationArrowStyles {
    private val arrow = ClassName {
        display = Display.inlineBlock
        cursor = pointer
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

    val arrowPrevious = ClassName(arrow) {
        span {
            marginLeft = (-3).px
            display = Display.block
            transform = scale(-1, 1)
            after {
                content = Content("➤")
            }
        }
    }

    val arrowNext = ClassName(arrow) {
        span {
            marginLeft = 3.px
            display = Display.block
            after {
                content = Content("➤")
            }
        }
    }
}