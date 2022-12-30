package pogolitics.view

import csstype.*
import emotion.react.css
import kotlinx.browser.window
import pogolitics.PageRProps
import pogolitics.cssClass
import pogolitics.model.BattleMode
import pogolitics.model.PokemonIndividualValuesState
import pogolitics.model.SinglePokemonModel
import pogolitics.view.component.*
import react.RBuilder
import react.RComponent
import react.RState
import styled.*
import react.SwitchSelector
import react.dom.html.ReactHTML.div
import kotlin.Float

class SinglePokemonPage(props: PageRProps<SinglePokemonModel, PokemonIndividualValuesState>) : RComponent<PageRProps<SinglePokemonModel, PokemonIndividualValuesState>, RState>(props) {

    override fun RBuilder.render() = renderPage(pokemonPage(props.model)) {
        div {
            attrs.css(Styles.headerWrapper)
            NavigationArrow {
               attrs {
                   href = pokemonPagePath(props.model.pokemon.pokedexNumber - 1, mode = props.model.mode)
                   direction = NavigationDirection.PREVIOUS
               }
            }
            styledSpan {
                attrs.css(Styles.spacer)
                SwitchSelector {
                    attrs {
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
            }
            NavigationArrow {
                attrs.href = pokemonPagePath(props.model.pokemon.pokedexNumber + 1, mode = props.model.mode)
                attrs.direction = NavigationDirection.NEXT
            }
        }
        div {
            attrs.css(Styles.leftWrapper)
            BasicPokemonInfo {
                attrs.data = props.model.pokemon
            }
        }
        div {
            attrs.css(Styles.rightWrapper)
            IVStatsWidget {
                attrs {
                    stats = props.model.stats.currentStats
                    ivs = props.model.stats.ivs
                    onChange = {
                        props.updateState(it)
                    }
                }
            }
            if (props.model.mode == BattleMode.PVP) {
                LeagueStatsWidget {
                    attrs {
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
                }
                LeagueStatsWidget {
                    attrs {
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
                }
                LeagueStatsWidget {
                    attrs {
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
        }
        div {
            attrs.css(Styles.leftWrapper)
            moveSetsTable {
                values = props.model.moveSets
            }
        }
        div {
            attrs.css(Styles.rightWrapper)
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

    private object Styles {
        const val smallScreenMediaQuery = "screen and (max-width: 700px)"

        val headerWrapper = cssClass {
            paddingTop = StyleConstants.Padding.small
            display = Display.flex
            fontSize = 160.pct
        }

        val arrow = cssClass {
            width = 42.px
            height = 42.px
            textAlign = TextAlign.center
            borderRadius = 50.pct
            marginLeft = StyleConstants.Margin.small
            marginRight = StyleConstants.Margin.small
            hover {
                color = StyleConstants.Colors.primary.text
                backgroundColor = StyleConstants.Colors.primary.bg
                textDecoration = None.none
            }
        }

        val spacer = cssClass {
            flexGrow = number(1.0)
            textAlign = TextAlign.center
        }

        val leftWrapper = cssClass {
            width = 50.pct
            float = csstype.Float.left
            /* media(smallScreenMediaQuery) {
                width = 100.pct
            }*/ // TODO later mig how to
        }

        val rightWrapper = cssClass {
            width = 50.pct
            float = csstype.Float.right
            /* media(smallScreenMediaQuery) {
                width = 100.pct
                float = Float.left
            } */ // TODO later mig how to
        }
    }
}
