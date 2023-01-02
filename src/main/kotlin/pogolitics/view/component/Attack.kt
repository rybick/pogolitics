package pogolitics.view.component

import csstype.Display
import csstype.Margin
import csstype.rem
import emotion.css.ClassName
import pogolitics.model.Attack
import pogolitics.view.StyleConstants
import pogolitics.view.iconPath
import react.*
import react.dom.html.ReactHTML.img
import emotion.react.css
import react.dom.html.ReactHTML.span

val Attack = FC<AttackProps> { props ->
    span {
        css(if (props.attack.elite) AttackStyles.eliteAttackWrapper else AttackStyles.regularAttackWrapper) {}
        img {
            src = iconPath(props.attack.type)
            css {
                height = 1.5.rem
                margin = Margin(StyleConstants.Margin.small, StyleConstants.Margin.small)
            }
        }
        + props.attack.name
    }
}

private object AttackStyles {
    val wrapper = ClassName {
        display = Display.inlineBlock
        paddingRight = StyleConstants.Padding.medium
        borderRadius = StyleConstants.Border.Radius.small
    }

    val regularAttackWrapper = ClassName(wrapper) {  }
    val eliteAttackWrapper = ClassName(wrapper) {
        color = StyleConstants.Colors.secondarySpecial.text
        backgroundColor = StyleConstants.Colors.secondarySpecial.bg
    }
}

external interface AttackProps: Props {
    var attack: Attack
}