package pogolitcs.view

import kotlinx.css.Float
import kotlinx.css.float
import kotlinx.css.pct
import kotlinx.css.width
import pogolitcs.PageRProps
import pogolitcs.model.PokemonIndividualValues
import pogolitcs.model.SinglePokemonModel
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.a
import react.dom.div
import styled.StyleSheet
import styled.css
import styled.styledDiv

class SinglePokemonPage(props: PageRProps<SinglePokemonModel, PokemonIndividualValues>) : RComponent<PageRProps<SinglePokemonModel, PokemonIndividualValues>, RState>(props) {

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
                cp = props.model.stats.cp
                ivs = props.state
                onChange = {
                    props.updateState(ivs)
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
