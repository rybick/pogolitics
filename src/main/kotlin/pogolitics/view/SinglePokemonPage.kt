package pogolitics.view

import emotion.react.css
import kotlinx.browser.window
import pogolitics.PageRProps
import pogolitics.model.BattleMode
import pogolitics.model.PokemonIndividualValuesState
import pogolitics.model.SinglePokemonModel
import pogolitics.view.component.*
import react.Component
import react.State
import react.SwitchSelector
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span
import pogolitics.view.SinglePokemonPageStyles as Styles

class SinglePokemonPage(props: PageRProps<SinglePokemonModel, PokemonIndividualValuesState>) :
    Component<PageRProps<SinglePokemonModel, PokemonIndividualValuesState>, State>(props) {

    override fun render() = renderPage(pokemonPage(props.model), props.model.pokemonIndex) {
        div {
            css(Styles.headerWrapper) {}
            NavigationArrow {
                active = props.model.pokemon.pokedexNumber > 1
                href = pokemonPagePath(props.model.pokemon.pokedexNumber - 1, mode = props.model.mode)
                direction = NavigationDirection.PREVIOUS
            }
            span {
                css(Styles.spacer) {}
                SwitchSelector {
                    checked = BattleMode.PVP == props.model.mode
                    onlabel = "PvP"
                    offlabel = "PvE"
                    onChange = { checked ->
                        // set timeout to let the animation end.
                        // It would be better to use state instead but this was easier, so I guess TODO one day.
                        window.setTimeout({
                            window.location.href = pokemonPagePath(
                                pokedexNumber = props.model.pokemon.pokedexNumber,
                                form = props.model.pokemon.form,
                                mode = if (checked) BattleMode.PVP else BattleMode.PVE
                            )
                        }, timeout = 300)
                    }
                }
            }
            NavigationArrow {
                active = true
                href = pokemonPagePath(props.model.pokemon.pokedexNumber + 1, mode = props.model.mode)
                direction = NavigationDirection.NEXT
            }
        }
        div {
            css(Styles.leftWrapper) {}
            BasicPokemonInfo {
                data = props.model.pokemon
            }
        }
        div {
            css(Styles.rightWrapper) {}
            IVStatsWidget {
                stats = props.model.stats.currentStats
                ivs = props.model.stats.ivs
                focus = props.model.focusedElement
                onChange = {
                    props.updateState(it)
                }
            }
            if (props.model.mode == BattleMode.PVP) {
                LeagueStatsWidget {
                    name = "great"
                    stats = props.model.stats.bestGreatLeagueStats
                    onClick = {
                        val newState = PokemonIndividualValuesState(
                            props.model.stats.ivs,
                            props.model.stats.bestGreatLeagueStats.level
                        )
                        props.updateState(newState)
                    }
                }
                LeagueStatsWidget {
                    name = "ultra"
                    stats = props.model.stats.bestUltraLeagueStats
                    onClick = {
                        val newState = PokemonIndividualValuesState(
                            props.model.stats.ivs,
                            props.model.stats.bestUltraLeagueStats.level
                        )
                        props.updateState(newState)
                    }
                }
                LeagueStatsWidget {
                    name = "master"
                    stats = props.model.stats.bestStatsWithoutBoost
                    onClick = {
                        val newState = PokemonIndividualValuesState(
                            props.model.stats.ivs,
                            props.model.stats.bestStatsWithoutBoost.level
                        )
                        props.updateState(newState)
                    }
                }
            }
        }
        div {
            css(Styles.leftWrapper) {}
            moveSetsTable {
                values = props.model.moveSets
                showAdditionalColumns = props.model.showAdditionalColumns
            }
        }
        div {
            css(Styles.rightWrapper) {}
            /* space for widgets that will always be last */
        }
    }

    private fun pokemonPage(model: SinglePokemonModel): Page =
        Page.POKEMON(
            pokedexNumber = model.pokemon.pokedexNumber,
            pokemonForm = model.pokemon.form,
            prettyName = model.pokemon.name,
            mode = model.mode
        )

}
