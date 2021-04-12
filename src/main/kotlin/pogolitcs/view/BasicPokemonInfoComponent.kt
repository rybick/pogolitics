package pogolitcs.view

import kotlinx.css.*
import pogolitcs.format
import pogolitcs.model.SinglePokemonModel
import react.RBuilder
import react.RComponent
import react.RProps
import react.ReactElement
import react.dom.span
import styled.*

class BasicPokemonInfoComponent(props: BasicPokemonInfoRProps) : RComponent<BasicPokemonInfoRProps, MovesetsRState>(props) {
    override fun RBuilder.render() {
        styledDiv {
            css { +BasicStylesheet.widgetWrapper }
            styledH1 {
                css { +Styles.wrapper }
                styledSpan {
                    css { +Styles.pokemonId }
                    +"#${props.data.id}"
                }
                span {
                    +props.data.name
                }
            }
            styledDiv {
                css { +Styles.staticStatsWrapper }
                staticStat { name = "Attack"; value = props.data.baseAttack.toString() }
                staticStat { name = "Defense"; value = props.data.baseDefense.toString() }
                staticStat { name = "Stamina"; value = props.data.baseStamina.toString() }
                staticStat {
                    name = "Hardiness"
                    value = props.data.hardiness.format(2)
                    info = "√(defense⋅stamina)"
                }
            }
        }
    }

    private object Styles: StyleSheet("BasicPokemonInfoComponentStyles", isStatic = true) {
        val wrapper by css {
            textAlign = TextAlign.center
        }

        val pokemonId by css {
            color = StyleConstants.Colors.secondary.secondaryText
            marginRight = StyleConstants.Margin.small
            fontSize = 80.pct
        }

        val staticStatsWrapper by css {
            textAlign = TextAlign.center
        }
    }
}

external interface BasicPokemonInfoRProps: RProps {
    var data: SinglePokemonModel.PokemonStaticInfo
}

fun RBuilder.basicPokemonInfo(handler: BasicPokemonInfoRProps.() -> Unit): ReactElement {
    return child(BasicPokemonInfoComponent::class) {
        this.attrs(handler)
    }
}