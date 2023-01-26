package pogolitics.view

import csstype.Border
import csstype.LineStyle
import csstype.None
import csstype.PropertiesBuilder
import csstype.px
import emotion.react.Global
import js.core.jso
import pogolitics.model.PokemonEntry
import pogolitics.view.component.Breadcrumbs
import pogolitics.view.component.Header
import react.ChildrenBuilder
import react.Fragment
import react.ReactNode
import react.create
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input

fun renderPage(
    page: Page?,
    pokemonIndex: List<PokemonEntry>?,
    contentRenderer: ChildrenBuilder.() -> Unit
): ReactNode = Fragment.create {
    Global {
        styles = jso<PropertiesBuilder> {
            a {
                color = StyleConstants.Colors.regularLink.text
                hover {
                    color = StyleConstants.Colors.regularLinkHovered.text
                    textDecoration = None.none
                }
            }
            input {
                border = Border(1.px, LineStyle.solid, StyleConstants.Colors.lightBorder)
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
    }
    div {
        if (pokemonIndex != null) {
            Header {
                this.pokemonIndex = pokemonIndex
            }
        }
        Breadcrumbs { this.page = page }
        contentRenderer()
    }
}