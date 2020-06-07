package pogolitcs.view

import pogolitcs.PageRProps
import pogolitcs.model.SinglePokemonModel
import react.RBuilder
import react.RComponent
import react.dom.a
import react.dom.div

class SinglePokemonPage(props: PageRProps<SinglePokemonModel>) : RComponent<PageRProps<SinglePokemonModel>, MovesetsRState>(props) {

    override fun RBuilder.render() {
        div {
            a(href = "/#/pokemon/${props.model.pokemon.id - 1}") { +"<" }
            a(href = "/#/pokemon/${props.model.pokemon.id + 1}") { +">" }
        }
        moveSetsTable {
            values = props.model.moveSets
        }
    }
}