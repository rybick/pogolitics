package pogolitics.view.component

import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import kotlinx.css.properties.scale
import kotlinx.css.properties.transform
import pogolitics.view.StyleConstants
import react.Props
import react.fc
import styled.*

val NavigationArrow = fc<ArrowProps> { props ->
    styledA(href = props.href) {
        css { +props.direction.style }
        styledSpan {}
    }
}

interface ArrowProps: Props {
    var direction: NavigationDirection
    var href: String
}

enum class NavigationDirection(val style: RuleSet) {
    PREVIOUS(NavigationArrowStyles.arrowPrevious), NEXT(NavigationArrowStyles.arrowNext);
}

private object NavigationArrowStyles: StyleSheet("NavigationArrowComponentStyles", isStatic = true) {
    val arrowPrevious by NavigationArrowStyles.css {
        +arrow
        span {
            marginLeft = (-3).px
            display = Display.block
            transform { scale(-1, 1) }
            after {
                content = QuotedString("➤")
            }
        }
    }

    val arrowNext by NavigationArrowStyles.css {
        +arrow
        span {
            marginLeft = 3.px
            display = Display.block
            after {
                content = QuotedString("➤")
            }
        }
    }

    private val arrow by NavigationArrowStyles.css {
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