package pogolitics.view.component

import csstype.ClassName
import csstype.Content
import csstype.Display
import csstype.FontFamily
import csstype.None
import csstype.TextAlign
import csstype.deg
import csstype.important
import csstype.pct
import csstype.px
import csstype.rotate
import csstype.scale
import emotion.css.ClassName
import emotion.react.css
import pogolitics.arial
import pogolitics.pointer
import pogolitics.view.StyleConstants
import react.FC
import react.Props
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.span

val NavigationArrow = FC<ArrowProps> { props ->
    a {
        href = props.href
        css(props.direction.style) {}
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
    private val triangleUp = Char(9662).toString()

    private val arrow = ClassName {
        display = Display.inlineBlock
        cursor = pointer
        width = 42.px
        height = 42.px
        fontFamily = arial
        fontSize = 48.px
        textAlign = TextAlign.center
        borderRadius = 50.pct
        marginLeft = StyleConstants.Margin.small
        marginRight = StyleConstants.Margin.small
        color = important(StyleConstants.Colors.primary.text)
        backgroundColor = StyleConstants.Colors.primary.bg
        hover {
            backgroundColor = StyleConstants.Colors.primaryHovered.bg
            color = StyleConstants.Colors.primary.text
            textDecoration = None.none
        }
    }

    val arrowPrevious = ClassName(arrow) {
        span {
            marginLeft = (-4).px
            marginTop = (-15).px
            display = Display.block
            transform = rotate(90.deg)
            after {
                content = Content(triangleUp)
            }
        }
    }

    val arrowNext = ClassName(arrow) {
        span {
            marginLeft = 4.px
            marginTop = (-14).px
            display = Display.block
            transform = rotate((-90).deg)
            after {
                content = Content(triangleUp)
            }
        }
    }
}