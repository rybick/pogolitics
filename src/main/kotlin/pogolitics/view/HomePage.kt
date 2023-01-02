package pogolitics.view

import emotion.react.css
import pogolitics.PageRProps
import react.Component
import react.attrs
import react.State
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p

class HomePage(props: PageRProps<Unit, Unit>) : Component<PageRProps<Unit, Unit>, State>(props) {
    override fun render() = renderPage(Page.HOME) {
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