package pogolitcs.view

import kotlinx.html.InputType
import kotlinx.html.js.*
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.KeyboardEvent
import pogolitcs.api.PokemonDto
import pogolitcs.model.PokemonIndividualValuesState
import pogolitcs.model.SinglePokemonModel
import react.*
import react.dom.defaultValue
import react.dom.input
import react.dom.key
import styled.*

class IVStatsWidget(props: IVStatsWidgetRProps) : RComponent<IVStatsWidgetRProps, RState>(props) {

    override fun RBuilder.render() {
        styledDiv {
            css {
               +Styles.statsWrapper
            }
            styledSpan {
                styledSpan {
                    +"level: "
                }
                styledSpan {
                    input(InputType.number) {
                        attrs.min = "1"
                        attrs.max = "41"
                        attrs.step = "0.5"
                        attrs.pattern = "\\d*"
                        attrs.key = "${props.ivs.level}"
                        attrs.defaultValue = "${props.ivs.level}"
                        val onChangeFunction = { event: Event ->
                            props.onChange(createStateWith { level = (event.target as HTMLInputElement).value.toFloat() })
                        }
                        attrs.onBlurFunction = onChangeFunction
                        attrs.onMouseUpFunction = onChangeFunction
                    }
                }
            }
        }
        ivBar {
            name = "Attack"
            iv = props.ivs.attack
            onChange = { value ->
                props.onChange(createStateWith { attack = value })
            }
        }
        ivBar {
            name = "Defense"
            iv = props.ivs.defense
            onChange = { value ->
                props.onChange(createStateWith { defense = value })
            }
        }
        ivBar {
            name = "HP"
            iv = props.ivs.stamina
            onChange = { value ->
                props.onChange(createStateWith { stamina = value })
            }
        }
        styledDiv {
            css {
               +Styles.statsWrapper
            }
            styledSpan {
                styledSpan {
                    +"CP: "
                }
                styledInput(InputType.number) {
                    css {
                        "&::-webkit-outer-spin-button" { put("-webkit-appearance", "none") }
                        "&::-webkit-inner-spin-button" { put("-webkit-appearance", "none") }
                        put("-moz-appearance", "textfield")
                    }
                    attrs.min = "10"
                    attrs.max = "5000"
                    attrs.key = "${props.ivs.cp}"
                    attrs.defaultValue = "${props.ivs.cp}"
                    val onChangeFunction = { event: Event ->
                        val value = (event.target as HTMLInputElement).value
                        props.onChange(createStateWith {
                            level = null
                            cp = value.toInt()
                        })
                    }
                    attrs.onBlurFunction = onChangeFunction
                    attrs.onMouseUpFunction = onChangeFunction
                    attrs.onKeyPressFunction = { event: Event ->
                        if (event.unsafeCast<KeyboardEvent>().key == "Enter") {
                            onChangeFunction(event)
                        }
                    }
                }
            }
        }
    }

    private fun createStateWith(modifier: PokemonIndividualValuesState.() -> Unit): PokemonIndividualValuesState {
        return PokemonIndividualValuesState(
            level = props.ivs.level,
            attack = props.ivs.attack,
            defense = props.ivs.defense,
            stamina = props.ivs.stamina,
            cp = null
        ).also(modifier)
    }

    private object Styles: StyleSheet("ComponentStyles", isStatic = true) {
        val statsWrapper by css {

        }
    }
}

external interface IVStatsWidgetRProps: RProps {
    var ivs: SinglePokemonModel.PokemonIndividualStatistics;
    var onChange: (PokemonIndividualValuesState) -> Unit
}

fun RBuilder.ivStatsWidget(handler: IVStatsWidgetRProps.() -> Unit): ReactElement {
    return child(IVStatsWidget::class) {
        this.attrs(handler)
    }
}