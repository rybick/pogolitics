package pogolitics.view

import csstype.Auto
import csstype.FontWeight
import csstype.px
import emotion.css.ClassName
import emotion.react.css
import pogolitics.PageRProps
import pogolitics.model.HomePageModel
import react.Component
import react.State
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.h3
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.strong

class HomePage(props: PageRProps<HomePageModel, Unit>) : Component<PageRProps<HomePageModel, Unit>, State>(props) {
    override fun render() = renderPage(Page.HOME, props.model.pokemonIndex) {
        div {
            css(Styles.outerWrapper) { }
            div {
                css(Styles.innerWrapper) {}
                h2 { +"Welcome to Poke-Go-Dex!" }
                p {+"""
                    The idea of this project is to create one place that contains all data about pokemon, 
                    attacks, etc in Pokemon Go and serve it in a processed form that is useful straight away. 
                """ }
                p { +"""
                    The main functionality right now is calculating DPS and other stats of each attack set of a given pokémon,
                    so that you can figure out which one is the best. There are already pages that do it, but all of them
                    work only for PvE. This page does it for both PvE and PvP (and that's the reason I started to work on it at all).
                """ }
                p {
                    strong { +"To try it out just use the search box in the top-right corner of the page and find the pokémon you want, " }
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
                h3 { +"What this project is not" }
                p {
                    +"It's not supposed to be a service with news about the game. "
                    +"There is a lot of such pages already and it keeps a lot of effort to keep such pages up to date. "
                    +"The information that you can find here, is the one that won't change next week. "
                }
            }
        }
    }

    object Styles {
        val outerWrapper = ClassName(BasicStylesheet.widgetWrapper) {
            paddingTop = StyleConstants.Padding.huge
        }

        val innerWrapper = ClassName {
            maxWidth = 800.px
            border = StyleConstants.Border.thickBorder
            borderRadius = StyleConstants.Border.Radius.big
            padding = StyleConstants.Padding.huge
            margin = Auto.auto

            h3 {
                fontSize = StyleConstants.Font.h3
                paddingTop = StyleConstants.Padding.big
            }
            a {
                fontWeight = FontWeight.bold
            }
        }
    }
}