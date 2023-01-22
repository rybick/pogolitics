package pogolitics.view.component

import dom.html.HTMLImageElement
import emotion.react.css
import pogolitics.format
import pogolitics.model.SinglePokemonModel
import pogolitics.view.BasicStylesheet
import pogolitics.view.pokemonImagePath
import react.*
import react.dom.events.SyntheticEvent
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.img
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
            css(BasicPokemonInfoStyles.pokemonPictureWrapper) {}
            img {
                css(BasicPokemonInfoStyles.pokemonPictureImg) {}
                src = pokemonImagePath(props.data.pokedexNumber, props.data.form, false)
                alt = "basic form"
                onError = ::hideOnError
            }
            img {
                css(BasicPokemonInfoStyles.pokemonPictureImg) {}
                src = pokemonImagePath(props.data.pokedexNumber, props.data.form, true)
                alt = "shiny form"
                onError = ::hideOnError
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

// TODO instead of hiding errors about missing images
// try to base pokemon index on getData(it)["moveSettings"] != null entries
// probably it should enable us to hide all technical/phantom pokemon like "default form Giratina"
private fun hideOnError(event: SyntheticEvent<HTMLImageElement, *>) {
    val target: HTMLImageElement = event.target as HTMLImageElement
    target.style.display = "none"
}

external interface BasicPokemonInfoRProps : Props {
    var data: SinglePokemonModel.PokemonStaticInfo
}