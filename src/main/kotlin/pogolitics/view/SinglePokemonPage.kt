package pogolitics.view

import kotlinx.browser.window
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import pogolitics.PageRProps
import pogolitics.model.BattleMode
import pogolitics.model.PokemonIndividualValuesState
import pogolitics.model.SinglePokemonModel
import pogolitics.view.component.Arrow
import pogolitics.view.component.ArrowDirection
import react.RBuilder
import react.RComponent
import react.RState
import styled.*
import react.SwitchSelector

class SinglePokemonPage(props: PageRProps<SinglePokemonModel, PokemonIndividualValuesState>) : RComponent<PageRProps<SinglePokemonModel, PokemonIndividualValuesState>, RState>(props) {

    override fun RBuilder.render() {
        styledDiv {
            css { +Styles.headerWrapper }
            Arrow {
               attrs {
                   href = pokemonPagePath(props.model.pokemon.pokedexNumber - 1, mode = props.model.mode)
                   direction = ArrowDirection.LEFT
               }
            }
            styledSpan {
                css { +Styles.spacer }
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
            Arrow {
                attrs {
                    href = pokemonPagePath(props.model.pokemon.pokedexNumber + 1, mode = props.model.mode)
                    direction = ArrowDirection.RIGHT
                }
            }
        }
        styledDiv {
            css { +Styles.leftWrapper }
            basicPokemonInfo {
                data = props.model.pokemon
            }
        }
        styledDiv {
            css { +Styles.rightWrapper }
            ivStatsWidget {
                stats = props.model.stats.currentStats
                ivs = props.model.stats.ivs
                onChange = {
                    props.updateState(it)
                }
            }
            if (props.model.mode == BattleMode.PVP) {
                leagueStatsWidget {
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
                leagueStatsWidget {
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
                leagueStatsWidget {
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
        styledDiv {
            css { +Styles.leftWrapper }
            moveSetsTable {
                values = props.model.moveSets
            }
        }
        styledDiv {
            css { +Styles.rightWrapper }
            /* space for widgets that will always be last */
        }
    }

    private object Styles: StyleSheet("ComponentStyles", isStatic = true) {
        const val smallScreenMediaQuery = "screen and (max-width: 700px)"

        val headerWrapper by css {
            paddingTop = StyleConstants.Padding.small
            display = Display.flex
            fontSize = 160.pct
        }

        val arrow by css {
            width = 42.px
            height = 42.px
            textAlign = TextAlign.center
            borderRadius = 50.pct
            marginLeft = StyleConstants.Margin.small
            marginRight = StyleConstants.Margin.small
            hover {
                color = StyleConstants.Colors.primary.text
                backgroundColor = StyleConstants.Colors.primary.bg
                textDecoration = TextDecoration.none
            }
        }

        val spacer by css {
            flexGrow = 1.0
            textAlign = TextAlign.center
        }

        val leftWrapper by css {
            width = 50.pct
            float = Float.left
            media(smallScreenMediaQuery) {
                width = 100.pct
            }
        }

        val rightWrapper by css {
            width = 50.pct
            float = Float.right
            media(smallScreenMediaQuery) {
                width = 100.pct
                float = Float.left
            }
        }
    }
}
