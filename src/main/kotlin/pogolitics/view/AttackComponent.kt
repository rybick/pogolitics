package pogolitics.view

import kotlinx.css.*
import pogolitics.model.Attack
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.span
import styled.StyleSheet
import styled.css
import styled.styledImg
import styled.styledSpan

class AttackComponent: RComponent<AttackComponentProps, RState>() {
    override fun RBuilder.render() {
        styledSpan {
            css { + Styles.wrapper }
            styledImg {
                css {
                    height = LinearDimension("1.5rem")
                    margin = "0 ${StyleConstants.Margin.small}"
                }
                attrs {
                    src = iconPath(props.attack.type)
                }
            }
            + props.attack.name
        }
    }

    private object Styles: StyleSheet("AttackComponentStyles", isStatic = true) {
        val wrapper by css {
            display = Display.inlineBlock
        }
    }
}

external interface AttackComponentProps: RProps {
    var attack: Attack
}