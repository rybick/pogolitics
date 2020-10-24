package pogolitcs.view

import kotlinx.css.Float
import kotlinx.css.float
import kotlinx.css.pct
import kotlinx.css.width
import pogolitcs.PageRProps
import pogolitcs.model.PokemonIndividualValuesState
import pogolitcs.model.SinglePokemonModel
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.a
import react.dom.div
import styled.StyleSheet
import styled.css
import styled.styledDiv

class SinglePokemonPage(props: PageRProps<SinglePokemonModel, PokemonIndividualValuesState>) : RComponent<PageRProps<SinglePokemonModel, PokemonIndividualValuesState>, RState>(props) {

    override fun RBuilder.render() {
        div {
            a(href = "/#/pokemon/${props.model.pokemon.id - 1}") { +"<" }
            a(href = "/#/pokemon/${props.model.pokemon.id + 1}") { +">" }
        }
        styledDiv {
            css { +Styles.column }
            basicPokemonInfo {
                data = props.model.pokemon
            }
            moveSetsTable {
                values = props.model.moveSets
            }
        }
        styledDiv {
            css { +Styles.column }
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
    }

    private object Styles: StyleSheet("ComponentStyles", isStatic = true) {
        val column by css {
            width = 50.pct
            float = Float.left
        }
    }
}
