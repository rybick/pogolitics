package pogolitics.view.component

import kotlinx.css.*
import kotlinx.html.InputType
import kotlinx.html.js.*
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.KeyboardEvent
import pogolitics.model.IVs
import pogolitics.model.PokemonIndividualValuesState
import pogolitics.model.SinglePokemonModel
import pogolitics.view.BasicStylesheet
import pogolitics.view.StyleConstants
import react.*
import react.dom.defaultValue
import react.dom.input
import react.dom.key
import styled.*

val IVStatsWidget = fc<IVStatsWidgetRProps> { props ->
    styledDiv {
        css {
            +BasicStylesheet.widgetWrapper
            +IVStatsWidgetStyles.outerWrapper
        }
        styledDiv {
            css { +IVStatsWidgetStyles.innerWrapper }
            styledDiv {
                css {
                    +IVStatsWidgetStyles.upperStatsWrapper
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
                                props.onChange(props.createStateWith {
                                    level = (event.target as HTMLInputElement).value.toFloat()
                                })
                            }
                            attrs.onBlurFunction = onChangeFunction
                            attrs.onMouseUpFunction = onChangeFunction
                        }
                    }
                }
            }
            IVBar {
                attrs {
                    name = "Attack"
                    iv = props.ivs.attack
                    onChange = { value ->
                        props.onChange(props.createStateWith { attack = value })
                    }
                }
            }
            IVBar {
                attrs {
                    name = "Defense"
                    iv = props.ivs.defense
                    onChange = { value ->
                        props.onChange(props.createStateWith { defense = value })
                    }
                }
            }
            IVBar {
                attrs {
                    name = "HP"
                    iv = props.ivs.stamina
                    onChange = { value ->
                        props.onChange(props.createStateWith { stamina = value })
                    }
                }
            }
            styledDiv {
                css {
                    +IVStatsWidgetStyles.lowerStatsWrapper
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
                            props.onChange(props.createStateWith {
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

private fun IVStatsWidgetRProps.createStateWith(modifier: PokemonIndividualValuesState.() -> Unit): PokemonIndividualValuesState {
    return PokemonIndividualValuesState(
        level = stats.level,
        attack = ivs.attack,
        defense = ivs.defense,
        stamina = ivs.stamina,
        cp = null
    ).also(modifier)
}

private object IVStatsWidgetStyles : StyleSheet("IVStatsWidgetStyles", isStatic = true) {
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
        padding = "${StyleConstants.Padding.semiBig}"
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

external interface IVStatsWidgetRProps : RProps {
    var stats: SinglePokemonModel.VariablePokemonStatistics
    var ivs: IVs
    var onChange: (PokemonIndividualValuesState) -> Unit
}