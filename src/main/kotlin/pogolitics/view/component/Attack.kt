package pogolitics.view.component

import csstype.Display
import emotion.css.ClassName
import pogolitics.model.Attack
import pogolitics.view.StyleConstants
import react.*
import emotion.react.css
import react.dom.html.ReactHTML.span

val Attack = FC<AttackProps> { props ->
    span {
        css(if (props.attack.elite) AttackStyles.eliteAttackWrapper else AttackStyles.regularAttackWrapper) {}
        PokemonTypeIcon {
            type = props.attack.type
            size = PokemonTypeIconSize.SMALL
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
        color = StyleConstants.Colors.regularSpecial.text
        backgroundColor = StyleConstants.Colors.regularSpecial.bg
    }
}

external interface AttackProps: Props {
    var attack: Attack
}