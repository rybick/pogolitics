package pogolitics.view.component

import csstype.Display
import csstype.None
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
            svgTriangle(props.direction)
        }
    }
}

fun ChildrenBuilder.svgTriangle(direction: NavigationDirection) {
    val boundingBoxSize: Double = NavigationArrowStyles.sizeInPixels.toDouble()
    val arrowSize: Double = NavigationArrowStyles.arrowSizeInPixels.toDouble()
    val halfArrowSize = arrowSize / 2
    val quarterArrowSize = arrowSize / 4
    val middle = boundingBoxSize / 2
    val (pointingX, tailX) = when (direction) {
        NavigationDirection.NEXT -> Pair(middle + halfArrowSize, middle - quarterArrowSize)
        NavigationDirection.PREVIOUS -> Pair(middle - halfArrowSize, middle + quarterArrowSize)
    }
    svg {
        width = boundingBoxSize
        height = boundingBoxSize
        polygon {
            points = "$pointingX,$middle $tailX,${middle - sqrt3 * quarterArrowSize} $tailX,${middle + sqrt3 * quarterArrowSize}"
            fill = StyleConstants.Colors.primary.text.toString()
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
    const val sizeInPixels = 43
    const val arrowSizeInPixels = 21

    val arrow = ClassName {
        display = Display.inlineBlock
        cursor = pointer
        width = sizeInPixels.px
        height = sizeInPixels.px
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

}