package pogolitics.view.component

import csstype.BoxSizing
import csstype.Display
import csstype.Position
import csstype.number
import csstype.px
import emotion.css.ClassName
import emotion.react.css
import pogolitics.model.PokemonEntry
import pogolitics.view.StyleConstants
import pogolitics.view.logoPath
import pogolitics.view.pokemonListPagePath
import react.FC
import react.Props
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.span
import react.useState

val Header = FC<HeaderProps> { props ->
    var term: String by useState("")
    val filtered = props.getFilteredData(term)

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
            input {
                value = term
                placeholder = "Search for pokemon..."
                onChange = { event ->
                    term = event.target.value
                }
            }
            div {
                css(HeaderStyles.searchResultsWrapper) {  }
                filtered.forEach {
                    div {
                        span { +"#${it.pokedexNumber} " }
                        span { +it.name }
                    }
                }
            }
        }
    }
}

private fun HeaderProps.getFilteredData(term: String): List<PokemonEntry> =
    if (term.isBlank()) {
        emptyList()
    } else {
        pokemonIndex.filter { it.name.contains(term, ignoreCase = true) }
    }

private data class Tmp(val id: Int, val name: String)

interface HeaderProps: Props {
    var pokemonIndex: List<PokemonEntry>
}

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