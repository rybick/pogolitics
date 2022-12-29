package pogolitics.view.component

import csstype.*
import dom.html.HTMLInputElement
import emotion.react.css
import pogolitics.cssClass
import pogolitics.model.IVs
import pogolitics.model.PokemonIndividualValuesState
import pogolitics.model.SinglePokemonModel
import pogolitics.plus
import pogolitics.view.BasicStylesheet
import pogolitics.view.StyleConstants
import react.*
import react.dom.events.KeyboardEvent
import react.dom.events.SyntheticEvent
import react.dom.html.InputType
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import styled.*

val IVStatsWidget = fc<IVStatsWidgetRProps> { props ->
    div {
        attrs.css(
            BasicStylesheet.widgetWrapper +
            IVStatsWidgetStyles.outerWrapper
        )
        styledDiv {
            attrs.css(IVStatsWidgetStyles.innerWrapper)
            styledDiv {
                attrs.css(IVStatsWidgetStyles.upperStatsWrapper)
                styledSpan {
                    styledSpan {
                        +"level: "
                    }
                    styledSpan {
                        input {
                            attrs.type = InputType.number
                            attrs.min = "1"
                            attrs.max = "51"
                            attrs.step = 0.5
                            attrs.pattern = "\\d*"
                            attrs.key = "${props.stats.level}"
                            attrs.defaultValue = "${props.stats.level}"
                            val onChangeFunction = { event: SyntheticEvent<*, *> ->
                                props.onChange(props.createStateWith {
                                    level = (event.target as HTMLInputElement).value.toFloat()
                                })
                            }
                            attrs.onBlur = onChangeFunction
                            attrs.onMouseUp = onChangeFunction
                        }
                    }
                }
            }
            IVBar {
                attrs.name = "Attack"
                attrs.iv = props.ivs.attack
                attrs.onChange = { value ->
                    props.onChange(props.createStateWith { attack = value })
                }
            }
            IVBar {
                attrs.name = "Defense"
                attrs.iv = props.ivs.defense
                attrs.onChange = { value ->
                    props.onChange(props.createStateWith { defense = value })
                }
            }
            IVBar {
                attrs.name = "HP"
                attrs.iv = props.ivs.stamina
                attrs.onChange = { value ->
                    props.onChange(props.createStateWith { stamina = value })
                }
            }
            styledDiv {
                attrs.css(IVStatsWidgetStyles.lowerStatsWrapper)
                styledSpan {
                    styledSpan {
                        +"CP: "
                    }
                    input() {
                        attrs.css {
                            "&::-webkit-outer-spin-button" {
                                //put("-webkit-appearance", "none")
                                //margin(0.px)
                            }
                            "&::-webkit-inner-spin-button" {
                                // put("-webkit-appearance", "none")
                                // margin(0.px)
                            }
                            // put("-moz-appearance", "textfield") // TODO later mig do those things
                        }
                        attrs.type = InputType.number
                        attrs.min = "10"
                        attrs.max = "5000"
                        attrs.key = "${props.stats.cp}"
                        attrs.defaultValue = "${props.stats.cp}"
                        val onChangeFunction = { event: SyntheticEvent<*, *> ->
                            val value = (event.target as HTMLInputElement).value
                            props.onChange(props.createStateWith {
                                level = null
                                cp = value.toInt()
                            })
                        }
                        attrs.onBlur = onChangeFunction
                        attrs.onKeyUp = { event: KeyboardEvent<*> ->
                            if (event.key == "Enter") {
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

private object IVStatsWidgetStyles {
    val outerWrapper = cssClass {
        display = Display.flex
        flexDirection = FlexDirection.column
        alignItems = AlignItems.center
        paddingBottom = StyleConstants.Padding.big
    }

    val innerWrapper = cssClass {
        display = Display.flex
        flexDirection = FlexDirection.column
        alignItems = AlignItems.center
        border = Border(StyleConstants.Border.thick, LineStyle.solid, StyleConstants.Colors.primary.bg)
        padding = StyleConstants.Padding.semiBig
        borderRadius = 20.px
    }

    val upperStatsWrapper = cssClass {
        marginTop = 10.px
        marginBottom = 15.px
    }

    val lowerStatsWrapper = cssClass {
        marginTop = 15.px
        marginBottom = 5.px
    }
}

external interface IVStatsWidgetRProps : RProps {
    var stats: SinglePokemonModel.VariablePokemonStatistics
    var ivs: IVs
    var onChange: (PokemonIndividualValuesState) -> Unit
}