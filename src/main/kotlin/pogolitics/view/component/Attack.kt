package pogolitics.view.component

import kotlinx.css.*
import pogolitics.model.Attack
import pogolitics.view.StyleConstants
import pogolitics.view.iconPath
import react.*
import styled.StyleSheet
import styled.css
import styled.styledImg
import styled.styledSpan

val Attack = fc<AttackProps> { props ->
    styledSpan {
        css { +AttackStyles.wrapper }
        styledImg(src = iconPath(props.attack.type)) {
            css {
                height = LinearDimension("1.5rem")
                margin = "0 ${StyleConstants.Margin.small}"
            }
        }
        + props.attack.name
    }
}

private object AttackStyles: StyleSheet("AttackComponentStyles", isStatic = true) {
    val wrapper by css {
        display = Display.inlineBlock
    }
}

external interface AttackProps: RProps {
    var attack: Attack
}