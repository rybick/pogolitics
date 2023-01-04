package pogolitics.view.component

import csstype.Border
import csstype.BoxSizing
import csstype.ClassName
import csstype.Display
import csstype.FlexDirection
import csstype.FontWeight
import csstype.JustifyContent
import csstype.LineStyle
import csstype.None
import csstype.Padding
import csstype.Position
import csstype.TextAlign
import csstype.div
import csstype.integer
import csstype.pct
import csstype.px
import csstype.unaryMinus
import dom.html.Window
import emotion.css.ClassName
import emotion.react.css
import pogolitics.KeyCodes
import pogolitics.model.PokemonEntry
import pogolitics.model.PokemonForm
import pogolitics.view.StyleConstants
import pogolitics.view.pokemonPagePath
import react.FC
import react.Props
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.span
import react.useState
import web.location.location

val SearchBox = FC<SearchBoxProps> { props ->
    val styles = SearchBoxStyles
    val searchResultLimit = 6
    var term: String by useState("")
    var selected: Int by useState(0)
    val filtered = props.getFilteredData(term).take(searchResultLimit)

    div {
        css(styles.wrapper) {}
        div {
            css(styles.inputWrapper) {}
            input {
                css(styles.input) {}
                value = term
                placeholder = "Search for pokemon..."
                onChange = { event ->
                    term = event.target.value
                }
                onKeyUp = { event ->
                    when (event.code) {
                        KeyCodes.enter -> {
                            val pokemon = filtered[selected]
                            val url = pokemonPagePath(pokemon.pokedexNumber, pokemon.form)
                            location.href = url
                        }
                        KeyCodes.arrowUp -> selected = (selected - 1).mod(filtered.size)
                        KeyCodes.arrowDown -> selected = (selected + 1).mod(filtered.size)
                        else -> selected = 0
                    }
                }
            }
        }
        div {
            css(styles.searchResultsWrapper) {}
            div {
                css(styles.searchResultsWrapperInner) {}
                filtered.forEachIndexed { index, (pokedexNumber, name, form) ->
                    a {
                        css(if (index == selected) styles.selectedEntryWrapper else styles.entryWrapper) {}
                        href = pokemonPagePath(pokedexNumber, form)
                        span {
                            css(styles.pokemonId) {}
                            +"#${pokedexNumber} "
                        }
                        span {
                            css(styles.pokemonName) {}
                            +name
                        }
                        span {
                            css(styles.pokemonForm) {}
                            if (form != PokemonForm.DEFAULT) {
                                +"(${form.prettyName})"
                            }
                        }
                    }
                }
                div {
                    css(styles.searchResultsFooter) {
                        display = if (filtered.isEmpty()) None.none else Display.block
                    }
                }
            }
        }
    }
}

private fun List<PokemonEntry>.toFormEntries(): List<PokemonFormEntry> =
    flatMap { entry ->
        entry.forms.map { form ->
            PokemonFormEntry(entry.pokedexNumber, entry.name, form)
        }
    }

interface SearchBoxProps: Props {
    var pokemonIndex: List<PokemonEntry>
}

fun SearchBoxProps.getFilteredData(term: String): List<PokemonFormEntry> {
    val searchTerms: List<String> = term.split("\\s")
    return if (term.isBlank()) {
        emptyList()
    } else {
        pokemonIndex
            .toFormEntries()
            .mapNotNull { entry -> entry.matches(searchTerms)?.let { Pair(it, entry) } }
            .sortedByDescending { (match, entry) -> calculateOrder(match, entry) }
            .map { (_, entry) -> entry }
    }
}

private object Order {
    val highest = 100
    val high = 99
    val lowest = 0
}
private fun calculateOrder(match: MatchedField, entry: PokemonFormEntry): Int =
    when (match) {
        MatchedField.NAME -> if (entry.form.isDefault()) Order.highest else Order.lowest
        MatchedField.POKEDEX_NUMBER -> Order.highest
        MatchedField.FORM -> Order.high
    }

enum class MatchedField {
    NAME,
    POKEDEX_NUMBER,
    FORM
}

data class PokemonFormEntry(val pokedexNumber: Int, val name: String, val form: PokemonForm) {
    fun matches(searchTerms: List<String>): MatchedField? {
        return searchTerms.firstNotNullOfOrNull { term ->
            if (name.contains(term, ignoreCase = true)) {
                MatchedField.NAME
            } else if (pokedexNumber.toString().contains(term)) {
                MatchedField.POKEDEX_NUMBER
            } else if (form.code?.contains(term, ignoreCase = true) == true) {
                MatchedField.FORM
            } else {
                null
            }
        }
    }
}

private object SearchBoxStyles {
    val wrapper = ClassName {}

    val inputWrapper = ClassName {
        textAlign = TextAlign.right
    }

    val searchResultsWrapper = ClassName {
        display = Display.flex
        justifyContent = JustifyContent.flexEnd
        flexDirection = FlexDirection.column
        position = Position.relative
        zIndex = integer(2)
        width = 100.pct
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
        display = Display.block
        padding = Padding(StyleConstants.Padding.medium, StyleConstants.Padding.big)
        color = backgroundColor
        hover {
            backgroundColor = StyleConstants.Colors.secondarySpecial.bg
            textDecoration = None.none
        }
    }

    val selectedEntryWrapper = ClassName(entryWrapper) {
        backgroundColor = StyleConstants.Colors.secondarySpecial.bg
        textDecoration = None.none
    }

    val pokemonId = ClassName {
        color = StyleConstants.Colors.secondary.secondaryText
        marginRight = StyleConstants.Margin.small
        fontSize = StyleConstants.Font.small
    }

    val pokemonName = ClassName {
        color = StyleConstants.Colors.secondary.text
    }

    val pokemonForm = ClassName {
        color = StyleConstants.Colors.secondary.secondaryText
        marginLeft = StyleConstants.Margin.small
        fontSize = 80.pct
    }

    val searchResultsFooter = ClassName {
        height = StyleConstants.Border.Radius.medium / 2// so that the content does not interlap with border radius
    }

    val input = ClassName {
        width = 100.pct
        border = 0.px
        padding = StyleConstants.Padding.small
        focus {
            outline = None.none
        }
    }
}