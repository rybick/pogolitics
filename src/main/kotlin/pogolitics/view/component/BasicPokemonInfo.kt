package pogolitics.view.component

import csstype.TextAlign
import csstype.pct
import emotion.css.ClassName
import emotion.react.css
import pogolitics.format
import pogolitics.model.SinglePokemonModel
import pogolitics.view.BasicStylesheet
import pogolitics.view.StyleConstants
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.span

val BasicPokemonInfo = fc<BasicPokemonInfoRProps> { props ->
    div {
        attrs.css(BasicStylesheet.widgetWrapper) {}
        h1 {
            attrs.css(BasicPokemonInfoStyles.wrapper) {}
            span {
                attrs.css(BasicPokemonInfoStyles.pokemonId) {}
                +"#${props.data.pokedexNumber}"
            }
            span {
                +props.data.name
            }
            props.data.form?.let { form ->
                span {
                    attrs.css(BasicPokemonInfoStyles.pokemonForm) {}
                    +"(${form.prettyName})"
                }
            }
        }
        div {
            attrs.css(BasicPokemonInfoStyles.staticStatsWrapper) {}
            StaticStat { attrs.name = "Attack";  attrs.value = props.data.baseAttack.toString() }
            StaticStat { attrs.name = "Defense"; attrs.value = props.data.baseDefense.toString() }
            StaticStat { attrs.name = "Stamina"; attrs.value = props.data.baseStamina.toString() }
            StaticStat {
                attrs.name = "Hardiness"
                attrs.value = props.data.hardiness.format(2)
                attrs.info = "(defense⋅stamina)"
            }
        }
    }
}

private object BasicPokemonInfoStyles {
    val wrapper = ClassName {
        textAlign = TextAlign.center
    }

    val pokemonId = ClassName {
        color = StyleConstants.Colors.secondary.secondaryText
        marginRight = StyleConstants.Margin.small
        fontSize = 80.pct
    }

    val pokemonForm = ClassName {
        color = StyleConstants.Colors.secondary.secondaryText
        marginLeft = StyleConstants.Margin.small
        fontSize = 80.pct
    }

    val staticStatsWrapper = ClassName {
        textAlign = TextAlign.center
    }
}

external interface BasicPokemonInfoRProps : Props {
    var data: SinglePokemonModel.PokemonStaticInfo
}