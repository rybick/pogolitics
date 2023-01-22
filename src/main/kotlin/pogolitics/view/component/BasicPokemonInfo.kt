package pogolitics.view.component

import emotion.react.css
import pogolitics.format
import pogolitics.model.SinglePokemonModel
import pogolitics.view.BasicStylesheet
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.span

val BasicPokemonInfo = FC<BasicPokemonInfoRProps> { props ->
    div {
        css(BasicStylesheet.widgetWrapper) {}
        h1 {
            css(BasicPokemonInfoStyles.wrapper) {}
            span {
                css(BasicPokemonInfoStyles.pokemonId) {}
                +"#${props.data.pokedexNumber}"
            }
            span {
                +props.data.name
            }
            props.data.form.prettyName?.let { prettyName ->
                span {
                    css(BasicPokemonInfoStyles.pokemonForm) {}
                    +"(${prettyName})"
                }
            }
        }
        div {
            css(BasicPokemonInfoStyles.typesWrapper) {}
            span {
                css(BasicPokemonInfoStyles.typeIconWrapper) {}
                PokemonTypeIcon {
                    type = props.data.types.primary
                    size = PokemonTypeIconProps.Size.BIG
                }
            }
            props.data.types.secondary?.let { secondary ->
                span {
                    css(BasicPokemonInfoStyles.typeIconWrapper) {}
                    PokemonTypeIcon {
                        type = secondary
                        size = PokemonTypeIconProps.Size.BIG
                    }
                }
            }
        }
        div {
            css(BasicPokemonInfoStyles.staticStatsWrapper) {}
            StaticStat { name = "Attack";  value = props.data.baseAttack.toString() }
            StaticStat { name = "Defense"; value = props.data.baseDefense.toString() }
            StaticStat { name = "Stamina"; value = props.data.baseStamina.toString() }
            StaticStat {
                name = "Hardiness"
                value = props.data.hardiness.format(2)
                info = "(defense⋅stamina)"
            }
        }
    }
}



external interface BasicPokemonInfoRProps : Props {
    var data: SinglePokemonModel.PokemonStaticInfo
}