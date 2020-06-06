package view

import MoveSetsTableDataMapper
import PageRProps
import PokemonController
import PokemonController.Model
import react.RBuilder
import react.RComponent
import react.RProps
import react.dom.a
import react.dom.div

class PokemonPage(props: PageRProps<Model>) : RComponent<PageRProps<Model>, MovesetsRState>(props) {
//    override fun view.PokemonPageRProps.init(props: view.PokemonPageRProps) {
//        sort = null
//    }

    override fun RBuilder.render() {
        div {
            a(href = "/#/pokemon/${props.model.pokemonData.id - 1}") { + "<" }
        }
        moveSetsTable {
            values = getMoveSets(props.model)
        }
    }
}

external interface PokemonPageRProps: RProps {
    var model: Model
}

// TODO
private fun getMoveSets(model: Model): List<MoveSet> {
    return MoveSetsTableDataMapper(model.pokemonData, model.fastMoves, model.chargedMoves)
        .getData()
}