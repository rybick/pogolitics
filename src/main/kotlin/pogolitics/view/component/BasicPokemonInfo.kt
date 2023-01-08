package pogolitics.view.component

import csstype.Margin
import csstype.TextAlign
import csstype.pct
import csstype.rem
import emotion.css.ClassName
import emotion.react.css
import pogolitics.format
import pogolitics.model.SinglePokemonModel
import pogolitics.view.BasicStylesheet
import pogolitics.view.StyleConstants
import pogolitics.view.iconPath
import react.*
import react.dom.html.ReactHTML
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
            css(BasicPokemonInfoStyles.typesWrapper) {}
            span {
                css(BasicPokemonInfoStyles.typeIconWrapper) {}
                img {
                    src = iconPath(props.data.types.primary)
                    css {
                        height = 2.5.rem
                        margin = Margin(StyleConstants.Margin.small, StyleConstants.Margin.small)
                    }
                }
            }
            props.data.types.secondary?.let { secondary ->
                span {
                    img {
                        src = iconPath(secondary)
                        css {
                            height = 2.5.rem
                            margin = Margin(StyleConstants.Margin.small, StyleConstants.Margin.small)
                        }
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

    val typesWrapper = ClassName {
        textAlign = TextAlign.center
        margin = StyleConstants.Margin.medium
    }

    val typeIconWrapper = ClassName {
        margin = StyleConstants.Margin.small
    }
}

external interface BasicPokemonInfoRProps : Props {
    var data: SinglePokemonModel.PokemonStaticInfo
}