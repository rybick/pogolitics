package pogolitics.view.component

import csstype.Display
import csstype.Margin
import csstype.rem
import pogolitics.cssClass
import pogolitics.model.Attack
import pogolitics.view.StyleConstants
import pogolitics.view.iconPath
import react.*
import react.dom.html.ReactHTML.img
import emotion.react.css
import react.dom.html.ReactHTML.span

val Attack = fc<AttackProps> { props ->
    span {
        attrs.css(AttackStyles.wrapper)
        img {
            attrs.src = iconPath(props.attack.type)
            attrs.css {
                height = 1.5.rem
                margin = Margin(StyleConstants.Margin.small, StyleConstants.Margin.small)
            }
        }
        + props.attack.name
    }
}

private object AttackStyles {
    val wrapper = cssClass {
        display = Display.inlineBlock
    }
}

external interface AttackProps: RProps {
    var attack: Attack
}