package pogolitics.view

import kotlinx.css.margin
import pogolitics.PageRProps
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.a
import react.dom.p
import styled.css
import styled.styledDiv

class HomePage(props: PageRProps<Unit, Unit>) : RComponent<PageRProps<Unit, Unit>, RState>(props) {
    override fun RBuilder.render() = renderPage(Page.HOME) {
        styledDiv {
            css {
                margin = "${StyleConstants.Margin.big}"
            }
            p { +"page under construction" }
            p {
                a(href = pokemonListPagePath()) {
                    +"go to list of pokemon"
                }
            }
        }
    }
}