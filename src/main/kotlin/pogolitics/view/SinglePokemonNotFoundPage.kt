package pogolitics.view

import emotion.react.css
import kotlinx.browser.window
import pogolitics.PageRProps
import pogolitics.model.BattleMode
import pogolitics.model.PokemonEntry
import pogolitics.model.PokemonIndividualValuesState
import pogolitics.view.component.NavigationArrow
import pogolitics.view.component.NavigationDirection
import react.Component
import react.State
import react.SwitchSelector
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.span
import pogolitics.view.SinglePokemonPageStyles

class SinglePokemonNotFoundPage :
    Component<PageRProps<SinglePokemonNotFoundModel, PokemonIndividualValuesState>, State>() {

    override fun render() = renderPage(pokemonNotFoundPage(props.model), props.model.pokemonIndex) {
        div {
            css(SinglePokemonPageStyles.headerWrapper) {}
            NavigationArrow {
                active = props.model.pokedexNumber > 1
                href = pokemonPagePath(props.model.pokedexNumber - 1, mode = props.model.mode)
                direction = NavigationDirection.PREVIOUS
            }
            ReactHTML.span {
                css(SinglePokemonPageStyles.spacer) {}
                SwitchSelector {
                    checked = BattleMode.PVP == props.model.mode
                    onlabel = "PvP"
                    offlabel = "PvE"
                    onChange = { checked ->
                        // set timeout to let the animation end.
                        // It would be better to use state instead but this was easier, so I guess TODO one day.
                        window.setTimeout({
                            window.location.href = pokemonPagePath(
                                pokedexNumber = props.model.pokedexNumber,
                                form = null,
                                mode = if (checked) BattleMode.PVP else BattleMode.PVE
                            )
                        }, timeout = 300)
                    }
                }
            }
            NavigationArrow {
                active = true
                href = pokemonPagePath(props.model.pokedexNumber + 1, mode = props.model.mode)
                direction = NavigationDirection.NEXT
            }
        }
        div {
            div {
                css(BasicStylesheet.widgetWrapper) {}
                h1 {
                    css(BasicPokemonInfoStyles.wrapper) {}
                    span {
                        css(BasicPokemonInfoStyles.pokemonId) {}
                        +"#${props.model.pokedexNumber}"
                    }
                    span {
                        +"No data for this pokemon"
                    }
                    span {
                        css(BasicPokemonInfoStyles.pokemonForm) {}
                        +"(yet)"
                    }
                }
            }
        }
    }

    private fun pokemonNotFoundPage(model: SinglePokemonNotFoundModel): Page =
        Page.POKEMON(
            pokedexNumber = model.pokedexNumber,
            pokemonForm = null,
            prettyName = "#${model.pokedexNumber}",
            mode = model.mode
        )
}

data class SinglePokemonNotFoundModel(
    val mode: BattleMode,
    val pokedexNumber: Int,
    val pokemonIndex: List<PokemonEntry>,
)