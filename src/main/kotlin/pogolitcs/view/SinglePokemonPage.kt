package pogolitcs.view

import kotlinx.css.Float
import kotlinx.css.float
import kotlinx.css.pct
import kotlinx.css.width
import pogolitcs.PageRProps
import pogolitcs.model.SinglePokemonModel
import react.RBuilder
import react.RComponent
import react.dom.a
import react.dom.div
import styled.StyleSheet
import styled.css
import styled.styledDiv

class SinglePokemonPage(props: PageRProps<SinglePokemonModel>) : RComponent<PageRProps<SinglePokemonModel>, MovesetsRState>(props) {

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
            div {
                +"there will be more components"
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