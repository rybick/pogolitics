package pogolitics.view.component

import csstype.*
import dom.html.HTMLInputElement
import emotion.css.ClassName
import emotion.react.css
import pogolitics.model.IVs
import pogolitics.model.PokemonIndividualValuesState
import pogolitics.model.SinglePokemonModel
import pogolitics.view.BasicStylesheet
import pogolitics.view.StyleConstants
import react.*
import react.dom.events.KeyboardEvent
import react.dom.events.SyntheticEvent
import react.dom.html.InputType
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.span
import styled.*

val IVStatsWidget = fc<IVStatsWidgetRProps> { props ->
    div {
        attrs.css(
            BasicStylesheet.widgetWrapper,
            IVStatsWidgetStyles.outerWrapper
        ) {}
        div {
            attrs.css(IVStatsWidgetStyles.innerWrapper) {}
            div {
                attrs.css(IVStatsWidgetStyles.upperStatsWrapper) {}
                span {
                    span {
                        +"level: "
                    }
                    span {
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
            div {
                attrs.css(IVStatsWidgetStyles.lowerStatsWrapper) {}
                span {
                    span {
                        +"CP: "
                    }
                    input {
                        attrs.css {
                            "&::-webkit-outer-spin-button" {
                                appearance = None.none
                                margin = 0.px
                            }
                            "&::-webkit-inner-spin-button" {
                                appearance = None.none
                                margin = 0.px
                            }
                            appearance = Appearance.textfield
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
    val outerWrapper = ClassName {
        display = Display.flex
        flexDirection = FlexDirection.column
        alignItems = AlignItems.center
        paddingBottom = StyleConstants.Padding.big
    }

    val innerWrapper = ClassName {
        display = Display.flex
        flexDirection = FlexDirection.column
        alignItems = AlignItems.center
        border = Border(StyleConstants.Border.thick, LineStyle.solid, StyleConstants.Colors.primary.bg)
        padding = StyleConstants.Padding.semiBig
        borderRadius = 20.px
    }

    val upperStatsWrapper = ClassName {
        marginTop = 10.px
        marginBottom = 15.px
    }

    val lowerStatsWrapper = ClassName {
        marginTop = 15.px
        marginBottom = 5.px
    }
}

external interface IVStatsWidgetRProps : RProps {
    var stats: SinglePokemonModel.VariablePokemonStatistics
    var ivs: IVs
    var onChange: (PokemonIndividualValuesState) -> Unit
}