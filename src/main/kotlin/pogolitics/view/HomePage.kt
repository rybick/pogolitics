package pogolitics.view

import csstype.Auto
import csstype.FontWeight
import csstype.Padding
import csstype.TextAlign
import csstype.px
import emotion.css.ClassName
import emotion.react.css
import pogolitics.PageRProps
import pogolitics.model.HomePageModel
import pogolitics.view.component.SearchBox
import react.Component
import react.State
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.h3
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.strong

// If you modify this page, copy the rendered content to index.html, for Google to see it.
class HomePage(props: PageRProps<HomePageModel, Unit>) : Component<PageRProps<HomePageModel, Unit>, State>(props) {
    override fun render() = renderPage(Page.HOME, props.model.pokemonIndex) {
        div {
            css(Styles.outerWrapper) { }
            div {
                css(Styles.searchBoxWrapper) {}
                div {
                    css(Styles.searchBoxWrapperInner) {}
                    SearchBox {
                        pokemonIndex = props.model.pokemonIndex
                    }
                }
            }
            div {
                css(Styles.textWrapper) {}
                h2 { +"Welcome to Poke-Go-Dex!" }
                p {+"""
                    The idea of this project is to create one place that contains all data about pokemon, 
                    attacks, etc in Pokemon Go and serve it in a processed form that is useful straight away. 
                """ }
                p { +"""
                    The main functionality right now is calculating DPS and other stats of each attack set of a given pokémon,
                    so that you can figure out which one is the best. There already other apps that do it, but all of them
                    work only for PvE. This page does it for both PvE and PvP (and that's the reason I started to work on it at all).
                """ }
                p {
                    strong { +"To try it out just use the search box above and find the pokémon you want, " }
                    +"or go to the "
                    a { +"the pokemon index page"; href = pokemonListPagePath() }
                    +"."
                }
                p {
                    +"If you have more questions check "
                    a { +"the FAQ page "; href = "https://github.com/rybick/pogolitics/blob/master/docs/FAQ.md" }
                    +"on github!"
                }
                p {
                    +"All code of Poke-Go-Dex is open source, so feel free to "
                    a { +"look through it "; href = "https://github.com/rybick/pogolitics" }
                    +"or even "
                    a { +"contribute your own"; href = "https://github.com/rybick/pogolitics/blob/master/docs/FAQ.md" }
                    +"."
                }
            }
        }
    }

    object Styles {
        val outerWrapper = ClassName(BasicStylesheet.widgetWrapper) {

        }

        val textWrapper = ClassName {
            maxWidth = 800.px
            margin = Auto.auto
            border = StyleConstants.Border.thickBorder
            borderRadius = StyleConstants.Border.Radius.big
            padding = StyleConstants.Padding.huge
            h2 {
                marginBottom = StyleConstants.Margin.big
                textAlign = TextAlign.center
            }
            h3 {
                fontSize = StyleConstants.Font.h3
                paddingTop = StyleConstants.Padding.big
            }
            a {
                fontWeight = FontWeight.bold
            }
        }

        val searchBoxWrapper = ClassName {
            maxWidth = 600.px
            margin = Auto.auto
        }

        val searchBoxWrapperInner = ClassName {
            border = StyleConstants.Border.thickBorder
            borderRadius = StyleConstants.Border.Radius.small
            margin = StyleConstants.Margin.huge
            height = 46.px
        }

    }
}