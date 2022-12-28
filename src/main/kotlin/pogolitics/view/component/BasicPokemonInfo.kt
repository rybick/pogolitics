package pogolitics.view.component

import kotlinx.css.*
import pogolitics.format
import pogolitics.model.SinglePokemonModel
import pogolitics.view.BasicStylesheet
import pogolitics.view.StyleConstants
import pogolitics.view.staticStat
import react.*
import react.dom.span
import styled.*

val BasicPokemonInfo = fc<BasicPokemonInfoRProps> { props ->
    styledDiv {
        css { +BasicStylesheet.widgetWrapper }
        styledH1 {
            css { +BasicPokemonInfoStyles.wrapper }
            styledSpan {
                css { +BasicPokemonInfoStyles.pokemonId }
                +"#${props.data.pokedexNumber}"
            }
            span {
                +props.data.name
            }
            props.data.form?.let { form ->
                styledSpan {
                    css { +BasicPokemonInfoStyles.pokemonForm }
                    +"(${form.prettyName})"
                }
            }
        }
        styledDiv {
            css { +BasicPokemonInfoStyles.staticStatsWrapper }
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

private object BasicPokemonInfoStyles : StyleSheet("BasicPokemonInfoComponentStyles", isStatic = true) {
    val wrapper by css {
        textAlign = TextAlign.center
    }

    val pokemonId by css {
        color = StyleConstants.Colors.secondary.secondaryText
        marginRight = StyleConstants.Margin.small
        fontSize = 80.pct
    }

    val pokemonForm by css {
        color = StyleConstants.Colors.secondary.secondaryText
        marginLeft = StyleConstants.Margin.small
        fontSize = 80.pct
    }

    val staticStatsWrapper by css {
        textAlign = TextAlign.center
    }
}


external interface BasicPokemonInfoRProps : RProps {
    var data: SinglePokemonModel.PokemonStaticInfo
}