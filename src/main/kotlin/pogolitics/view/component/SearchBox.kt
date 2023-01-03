package pogolitics.view.component

import csstype.Border
import csstype.BoxSizing
import csstype.Display
import csstype.LineStyle
import csstype.Padding
import csstype.Position
import csstype.number
import csstype.pct
import csstype.px
import csstype.unaryMinus
import emotion.css.ClassName
import emotion.react.css
import pogolitics.model.PokemonEntry
import pogolitics.view.StyleConstants
import pogolitics.view.pokemonPagePath
import react.FC
import react.Props
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.span
import react.useState

val SearchBox = FC<SearchBoxProps> { props ->
    val searchResultLimit = 6
    var term: String by useState("")
    val filtered = props.getFilteredData(term).take(searchResultLimit)

    div {
        css(SearchBoxStyles.wrapper) {}
        input {
            value = term
            placeholder = "Search for pokemon..."
            onChange = { event ->
                term = event.target.value
            }
        }
        div {
            css(SearchBoxStyles.searchResultsWrapper) {}
            div {
                css(SearchBoxStyles.searchResultsWrapperInner) {}
                filtered.forEach {
                    div {
                        css(SearchBoxStyles.entryWrapper) {}
                        span { +"#${it.pokedexNumber} " }
                        span {
                            a {
                                href = pokemonPagePath(it.pokedexNumber, null)
                                +it.name
                            }
                        }
                    }
                }
            }
        }
    }
}

interface SearchBoxProps: Props {
    var pokemonIndex: List<PokemonEntry>
}

private fun SearchBoxProps.getFilteredData(term: String): List<PokemonEntry> =
    if (term.isBlank()) {
        emptyList()
    } else {
        pokemonIndex.filter { it.name.contains(term, ignoreCase = true) }
    }

private object SearchBoxStyles {
    val wrapper = ClassName {}

    val searchResultsWrapper = ClassName {
        position = Position.relative
        width = 100.pct
        paddingLeft = 1.px
        paddingRight = 1.px
    }

    val searchResultsWrapperInner = ClassName {
        backgroundColor = StyleConstants.Colors.secondary.bg
        width = 100.pct
        borderLeft = Border(StyleConstants.Border.thick, LineStyle.solid, StyleConstants.Colors.primary.bg)
        borderRight = Border(StyleConstants.Border.thick, LineStyle.solid, StyleConstants.Colors.primary.bg)
        borderBottom = Border(StyleConstants.Border.thick, LineStyle.solid, StyleConstants.Colors.primary.bg)
        borderBottomLeftRadius = StyleConstants.Border.Radius.medium
        borderBottomRightRadius = StyleConstants.Border.Radius.medium
        boxSizing = BoxSizing.contentBox
        marginLeft = -StyleConstants.Border.thick
    }

    val entryWrapper = ClassName {
        padding = Padding(5.px, 10.px)
    }
}