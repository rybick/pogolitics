package pogolitcs.view

import pogolitcs.model.Attack
import pogolitcs.model.Style
import kotlinx.css.LinearDimension
import kotlinx.css.height
import kotlinx.css.margin
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.span
import styled.css
import styled.styledImg

class AttackComponent: RComponent<AttackComponentProps, RState>() {
    override fun RBuilder.render() {
        span {
            styledImg {
                css {
                    height = LinearDimension("1.5rem")
                    margin = "0 ${Style.Margin.small}"
                }
                attrs {
                    src = iconPath(props.attack.type)
                }
            }
            + props.attack.name
        }
    }
}

external interface AttackComponentProps: RProps {
    var attack: Attack
}