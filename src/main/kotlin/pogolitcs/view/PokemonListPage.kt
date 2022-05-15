package pogolitcs.view

import pogolitcs.PageRProps
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.p

class PokemonListPage(props: PageRProps<Unit, Unit>) : RComponent<PageRProps<Unit, Unit>, RState>(props) {
    override fun RBuilder.render() {
        p { +"here be pokemon" }
    }
}