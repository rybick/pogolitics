package pogolitics.view

import csstype.None
import emotion.react.css
import pogolitics.model.PokemonEntry
import pogolitics.view.component.Breadcrumbs
import pogolitics.view.component.Header
import react.ChildrenBuilder
import react.Fragment
import react.ReactNode
import react.create
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div

fun renderPage(
    page: Page?,
    pokemonIndex: List<PokemonEntry>?,
    contentRenderer: ChildrenBuilder.() -> Unit
): ReactNode = Fragment.create {
    // TODO I think Global should be used here, but I don't know how to use it
    div {
        css {
            a {
                color = StyleConstants.Colors.secondaryLink.text
                hover {
                    color = StyleConstants.Colors.secondaryLinkHovered.text
                    textDecoration = None.none
                }
            }
            ".btn-primary" {
                backgroundColor = StyleConstants.Colors.primary.bg
                borderColor = StyleConstants.Colors.primary.bg
            }
            ".btn-primary:hover" {
                backgroundColor = StyleConstants.Colors.primaryHovered.bg
                borderColor = StyleConstants.Colors.primaryHovered.bg
            }
            ".btn-primary:not(.disabled):not(:disabled):active" {
                backgroundColor = StyleConstants.Colors.primaryHovered.bg
                borderColor = StyleConstants.Colors.primaryHovered.bg
            }
        }
        if (pokemonIndex != null) {
            Header {
                this.pokemonIndex = pokemonIndex
            }
        }
        Breadcrumbs { this.page = page }
        contentRenderer()
    }
}