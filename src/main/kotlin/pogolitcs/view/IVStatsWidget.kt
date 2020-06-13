package pogolitcs.view

import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import pogolitcs.model.PokemonIndividualValues
import react.*
import react.dom.defaultValue
import react.dom.input
import react.dom.span
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledSpan

class IVStatsWidget(props: IVStatsWidgetRProps) : RComponent<IVStatsWidgetRProps, RState>(props) {

    override fun RBuilder.render() {
        styledDiv {
            css {
               +Styles.statsWrapper
            }
            styledSpan {
                styledSpan {
                    +"level"
                }
                styledSpan {
                    input(InputType.number) {
                        attrs.min = "1"
                        attrs.max = "41"
                        attrs.step = "0.5"
                        attrs.pattern = "\\d*"
                        attrs.defaultValue = "40.0"
                        attrs.onChangeFunction = { event ->
                            props.onChange(props.ivs.apply { level = (event.target as HTMLInputElement).value.toFloat() })
                        }
                    }
                }
            }
            styledSpan {
                styledSpan {
                    +"CP"
                }
                styledSpan {
                    +"${props.cp}"
                }
            }
        }
        ivBar {
            name = "Attack"
            iv = props.ivs.attack
            onChange = { value ->
                props.onChange(props.ivs.apply { attack = value })
            }
        }
        ivBar {
            name = "Defense"
            iv = props.ivs.defense
            onChange = { value ->
                props.onChange(props.ivs.apply { defense = value })
            }
        }
        ivBar {
            name = "HP"
            iv = props.ivs.stamina
            onChange = { value ->
                props.onChange(props.ivs.apply { stamina = value })
            }
        }
    }

    private object Styles: StyleSheet("ComponentStyles", isStatic = true) {
        val statsWrapper by css {

        }
    }
}

external interface IVStatsWidgetRProps: RProps {
    var cp: Int
    var ivs: PokemonIndividualValues;
    var onChange: (PokemonIndividualValues) -> Unit
}

fun RBuilder.ivStatsWidget(handler: IVStatsWidgetRProps.() -> Unit): ReactElement {
    return child(IVStatsWidget::class) {
        this.attrs(handler)
    }
}