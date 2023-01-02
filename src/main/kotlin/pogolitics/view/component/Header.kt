package pogolitics.view.component

import csstype.BoxSizing
import csstype.Display
import csstype.Position
import csstype.number
import csstype.px
import emotion.css.ClassName
import emotion.react.css
import pogolitics.view.StyleConstants
import pogolitics.view.logoPath
import pogolitics.view.pokemonListPagePath
import react.FC
import react.Props
import react.dom.html.ReactHTML.a
import react.SearchInput
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.span
import react.useState
import web.prompts.alert

val Header = FC<HeaderProps> {
    var term: String by useState("Pikachu")
    val filtered = data.filter { it.name.contains(term) }

    div {
        css(HeaderStyles.headerWrapper) {}
        div {
            css(HeaderStyles.logoWrapper) {}
            a { // TODO lead to main page when there is anything there
                href = pokemonListPagePath()
                img { src = logoPath() }
            }
        }
        div {
            css(HeaderStyles.searchInputWrapper) {}
            SearchInput {
                value = term
                onChange = { newTerm ->
                    term = newTerm
                }
            }
            div {
                css(HeaderStyles.searchResultsWrapper) {  }
                filtered.forEach {
                    div {
                        span { +"#${it.id} " }
                        span { +it.name }
                    }
                }
            }
        }
    }
}

private val data = listOf<Tmp>(
    Tmp(1, "Pikachu"),
    Tmp(2, "Bulbasaur"),
    Tmp(2, "Charmander")
)

private data class HeaderState(var term: String)

private data class Tmp(val id: Int, val name: String)

interface HeaderProps: Props

private object HeaderStyles {
    val headerWrapper = ClassName {
        display = Display.flex
        boxSizing = BoxSizing.contentBox
        height = 60.px
        paddingTop = StyleConstants.Padding.medium
        paddingBottom = StyleConstants.Padding.medium
        paddingLeft = StyleConstants.Padding.medium
        paddingRight = StyleConstants.Padding.medium
        backgroundColor = StyleConstants.Colors.primary.bg
    }

    val logoWrapper = ClassName {
        flex = number(1.0)
    }

    val searchInputWrapper = ClassName {}

    val searchResultsWrapper = ClassName {
        position = Position.absolute
    }
}