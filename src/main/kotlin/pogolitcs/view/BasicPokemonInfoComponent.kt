package pogolitcs.view

import kotlinx.css.*
import pogolitcs.model.SinglePokemonModel
import react.RBuilder
import react.RComponent
import react.RProps
import react.ReactElement
import react.dom.span
import styled.*

class BasicPokemonInfoComponent(props: BasicPokemonInfoRProps) : RComponent<BasicPokemonInfoRProps, MovesetsRState>(props) {
    override fun RBuilder.render() {
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
    }

    private object Styles: StyleSheet("ComponentStyles", isStatic = true) {
        val wrapper by css {
            textAlign = TextAlign.center
        }

        val pokemonId by css {
            color = StyleConstants.colors.secondary.secondaryFont
            marginRight = StyleConstants.margin.small
            fontSize = 80.pct
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