package pogolitics.view

import emotion.react.css
import pogolitics.PageRProps
import react.RBuilder
import react.RComponent
import react.State
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p

class HomePage(props: PageRProps<Unit, Unit>) : RComponent<PageRProps<Unit, Unit>, State>(props) {
    override fun RBuilder.render() = renderPage(Page.HOME) {
        div {
            attrs.css(BasicStylesheet.widgetWrapper) {}
            attrs.css {
                margin = StyleConstants.Margin.big
            }
            p { +"page under construction" }
            p {
                a {
                    attrs.href = pokemonListPagePath()
                    +"go to list of pokemon"
                }
            }
        }
    }
}