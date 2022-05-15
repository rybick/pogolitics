package pogolitcs.view

import kotlinx.css.*
import pogolitcs.PageRProps
import pogolitcs.model.PokemonIndividualValuesState
import pogolitcs.model.SinglePokemonModel
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.a
import styled.*

class SinglePokemonPage(props: PageRProps<SinglePokemonModel, PokemonIndividualValuesState>) : RComponent<PageRProps<SinglePokemonModel, PokemonIndividualValuesState>, RState>(props) {

    override fun RBuilder.render() {
        styledDiv {
            css { +Styles.headerWrapper }
            a(href = pokemonPagePath(props.model.pokemon.familyId - 1)) { +"<" }
            styledSpan { css { +Styles.spacer } }
            a(href = pokemonPagePath(props.model.pokemon.familyId + 1)) { +">" }
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
            leagueStatsWidget {
                name = "great"
                stats = props.model.stats.bestGreatLeagueStats
                onClick = {
                    val newState = PokemonIndividualValuesState(props.model.stats.ivs, props.model.stats.bestGreatLeagueStats.level)
                    props.updateState(newState)
                }
            }
            leagueStatsWidget {
                name = "ultra"
                stats = props.model.stats.bestUltraLeagueStats
                onClick = {
                    val newState = PokemonIndividualValuesState(props.model.stats.ivs, props.model.stats.bestUltraLeagueStats.level)
                    props.updateState(newState)
                }
            }
            leagueStatsWidget {
                name = "master"
                stats = props.model.stats.bestStatsWithoutBoost
                onClick = {
                    val newState = PokemonIndividualValuesState(props.model.stats.ivs, props.model.stats.bestStatsWithoutBoost.level)
                    props.updateState(newState)
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
            display = Display.flex
            fontSize = 160.pct
            a {
                paddingLeft = StyleConstants.Padding.big
                paddingRight = StyleConstants.Padding.big
            }
        }

        val spacer by css {
            flexGrow = 1.0
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
