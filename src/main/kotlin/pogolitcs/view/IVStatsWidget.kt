package pogolitcs.view

import kotlinx.css.*
import kotlinx.html.InputType
import kotlinx.html.js.*
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.KeyboardEvent
import pogolitcs.model.IVs
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
                +BasicStylesheet.widgetWrapper
                +Styles.outerWrapper
            }
            styledDiv {
                css { +Styles.innerWrapper }
                styledDiv {
                    css {
                        +Styles.upperStatsWrapper
                    }
                    styledSpan {
                        styledSpan {
                            +"level: "
                        }
                        styledSpan {
                            input(InputType.number) {
                                attrs.min = "1"
                                attrs.max = "51"
                                attrs.step = "0.5"
                                attrs.pattern = "\\d*"
                                attrs.key = "${props.stats.level}"
                                attrs.defaultValue = "${props.stats.level}"
                                val onChangeFunction = { event: Event ->
                                    props.onChange(createStateWith {
                                        level = (event.target as HTMLInputElement).value.toFloat()
                                    })
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
                        +Styles.lowerStatsWrapper
                    }
                    styledSpan {
                        styledSpan {
                            +"CP: "
                        }
                        styledInput(InputType.number) {
                            css {
                                "&::-webkit-outer-spin-button" {
                                    put("-webkit-appearance", "none")
                                    margin(0.px)
                                }
                                "&::-webkit-inner-spin-button" {
                                    put("-webkit-appearance", "none")
                                    margin(0.px)
                                }
                                put("-moz-appearance", "textfield")
                            }
                            attrs.min = "10"
                            attrs.max = "5000"
                            attrs.key = "${props.stats.cp}"
                            attrs.defaultValue = "${props.stats.cp}"
                            val onChangeFunction = { event: Event ->
                                val value = (event.target as HTMLInputElement).value
                                props.onChange(createStateWith {
                                    level = null
                                    cp = value.toInt()
                                })
                            }
                            attrs.onBlurFunction = onChangeFunction
                            attrs.onKeyPressFunction = { event: Event ->
                                if (event.unsafeCast<KeyboardEvent>().key == "Enter") {
                                    onChangeFunction(event)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun createStateWith(modifier: PokemonIndividualValuesState.() -> Unit): PokemonIndividualValuesState {
        return PokemonIndividualValuesState(
            level = props.stats.level,
            attack = props.ivs.attack,
            defense = props.ivs.defense,
            stamina = props.ivs.stamina,
            cp = null
        ).also(modifier)
    }

    private object Styles: StyleSheet("IVStatsWidgetStyles", isStatic = true) {
        val outerWrapper by css {
            display = Display.flex
            flexDirection = FlexDirection.column
            alignItems = Align.center
            paddingBottom = StyleConstants.Padding.big
        }

        val innerWrapper by css {
            display = Display.flex
            flexDirection = FlexDirection.column
            alignItems = Align.center
            border = "${StyleConstants.Border.thick} solid ${StyleConstants.Colors.primary.bg}"
            padding = "${StyleConstants.Padding.big}"
            borderRadius = 20.px
        }

        val upperStatsWrapper by css {
            marginTop = 10.px
            marginBottom = 15.px
        }

        val lowerStatsWrapper by css {
            marginTop = 15.px
            marginBottom = 5.px
        }
    }
}

external interface IVStatsWidgetRProps: RProps {
    var stats: SinglePokemonModel.VariablePokemonStatistics
    var ivs: IVs
    var onChange: (PokemonIndividualValuesState) -> Unit
}

fun RBuilder.ivStatsWidget(handler: IVStatsWidgetRProps.() -> Unit): ReactElement {
    return child(IVStatsWidget::class) {
        this.attrs(handler)
    }
}