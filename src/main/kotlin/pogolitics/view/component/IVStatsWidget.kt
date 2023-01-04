package pogolitics.view.component

import csstype.*
import dom.html.HTMLInputElement
import emotion.css.ClassName
import emotion.react.css
import pogolitics.KeyCodes
import pogolitics.model.IVs
import pogolitics.model.PokemonIndividualValuesState
import pogolitics.model.SinglePokemonModel
import pogolitics.model.SinglePokemonModel.InputElement
import pogolitics.view.BasicStylesheet
import pogolitics.view.StyleConstants
import react.*
import react.dom.events.KeyboardEvent
import react.dom.events.SyntheticEvent
import react.dom.html.InputType
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.span

val IVStatsWidget = FC<IVStatsWidgetRProps> { props ->
    div {
        css(
            BasicStylesheet.widgetWrapper,
            IVStatsWidgetStyles.outerWrapper
        ) {}
        div {
            css(IVStatsWidgetStyles.innerWrapper) {}
            div {
                css(IVStatsWidgetStyles.upperStatsWrapper) {}
                span {
                    span {
                        +"level: "
                    }
                    span {
                        input {
                            type = InputType.number
                            min = "1"
                            max = "51"
                            step = 0.5
                            pattern = "\\d*"
                            key = "${props.stats.level}"
                            defaultValue = "${props.stats.level}"
                            autoFocus = props.focus == InputElement.IV
                            val onChangeFunction = { event: SyntheticEvent<*, *> ->
                                props.onChange(props.createStateWith {
                                    focus = InputElement.IV
                                    level = (event.target as HTMLInputElement).value.toFloat()
                                })
                            }
                            onChange = onChangeFunction
                        }
                    }
                }
            }
            IVBar {
                name = "Attack"
                iv = props.ivs.attack
                autoFocus = props.focus == InputElement.ATTACK
                onChange = { value ->
                    props.onChange(props.createStateWith { focus = InputElement.ATTACK; attack = value })
                }
            }
            IVBar {
                name = "Defense"
                iv = props.ivs.defense
                autoFocus = props.focus == InputElement.DEFENSE
                onChange = { value ->
                    props.onChange(props.createStateWith { focus = InputElement.DEFENSE; defense = value })
                }
            }
            IVBar {
                name = "HP"
                iv = props.ivs.stamina
                autoFocus = props.focus == InputElement.STAMINA
                onChange = { value ->
                    props.onChange(props.createStateWith { focus = InputElement.STAMINA; stamina = value })
                }
            }
            div {
                css(IVStatsWidgetStyles.lowerStatsWrapper) {}
                span {
                    span {
                        +"CP: "
                    }
                    input {
                        css {
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
                        type = InputType.number
                        min = "10"
                        max = "5000"
                        key = "${props.stats.cp}"
                        defaultValue = "${props.stats.cp}"
                        val onChangeFunction = { event: SyntheticEvent<*, *> ->
                            val value = (event.target as HTMLInputElement).value
                            props.onChange(props.createStateWith {
                                level = null
                                cp = value.toInt()
                            })
                        }
                        onBlur = onChangeFunction
                        onKeyUp = { event: KeyboardEvent<*> ->
                            if (event.key == KeyCodes.enter) {
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
        borderRadius = StyleConstants.Border.Radius.big
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

external interface IVStatsWidgetRProps : Props {
    var stats: SinglePokemonModel.VariablePokemonStatistics
    var ivs: IVs
    var onChange: (PokemonIndividualValuesState) -> Unit
    var focus: InputElement?
}