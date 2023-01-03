package pogolitics.view

import emotion.react.css
import pogolitics.PageRProps
import pogolitics.model.HomePageModel
import react.Component
import react.State
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p

class HomePage(props: PageRProps<HomePageModel, Unit>) : Component<PageRProps<HomePageModel, Unit>, State>(props) {
    override fun render() = renderPage(Page.HOME, props.model.pokemonIndex) {
        div {
            css(BasicStylesheet.widgetWrapper) {}
            css {
                margin = StyleConstants.Margin.big
            }
            p { +"page under construction" }
            p {
                a {
                    href = pokemonListPagePath()
                    +"go to list of pokemon"
                }
            }
        }
    }
}