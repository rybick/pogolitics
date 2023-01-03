package pogolitics.view

import pogolitics.model.PokemonEntry
import pogolitics.view.component.Breadcrumbs
import pogolitics.view.component.Header
import react.ChildrenBuilder
import react.Fragment
import react.ReactNode
import react.create

fun renderPage(
    page: Page?,
    pokemonIndex: List<PokemonEntry>,
    contentRenderer: ChildrenBuilder.() -> Unit
): ReactNode = Fragment.create {
    Header {
        this.pokemonIndex = pokemonIndex
    }
    Breadcrumbs { this.page = page }
    contentRenderer()
}