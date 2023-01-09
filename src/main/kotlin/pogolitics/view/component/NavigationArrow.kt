package pogolitics.view.component

import csstype.Display
import csstype.None
import csstype.Position
import csstype.TextAlign
import csstype.important
import csstype.pct
import csstype.px
import emotion.css.ClassName
import emotion.react.css
import pogolitics.pointer
import pogolitics.view.StyleConstants
import react.ChildrenBuilder
import react.FC
import react.Props
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.span
import react.dom.svg.ReactSVG.polygon
import react.dom.svg.ReactSVG.svg
import kotlin.math.sqrt

val NavigationArrow = FC<ArrowProps> { props ->
    a {
        href = props.href
        css(NavigationArrowStyles.arrow) {}
        span {
            svgTriangle(20.0, props.direction)
        }
    }
}

fun ChildrenBuilder.svgTriangle(size: Double, direction: NavigationDirection) {
    val middle = size / 2
    val (pointingX, tailX) = when (direction) {
        NavigationDirection.NEXT -> Pair(size, 0.25*size)
        NavigationDirection.PREVIOUS -> Pair(0.0, 0.75*size)
    }
    svg {
        width = size
        height = size
        polygon {
            points = "$pointingX,$middle $tailX,${(2 + sqrt3) * size / 4} $tailX,${(2 - sqrt3) * size / 4}"
            style = js("{\"fill\":\"white\"}") // TODO it uses hardcoded white, because css {} does not support fill
        }
    }
}

private val sqrt3 = sqrt(3.0)

interface ArrowProps: Props {
    var direction: NavigationDirection
    var href: String
}

enum class NavigationDirection {
    PREVIOUS, NEXT;
}

private object NavigationArrowStyles {

    val arrow = ClassName {
        display = Display.inlineBlock
        cursor = pointer
        width = 42.px
        height = 42.px
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
        span {
            position = Position.relative
            top = 1.px
        }
    }

}