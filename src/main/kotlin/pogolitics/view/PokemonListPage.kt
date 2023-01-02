package pogolitics.view

import csstype.*
import emotion.css.ClassName
import emotion.react.css
import pogolitics.PageRProps
import pogolitics.model.PokemonListModel
import react.ChildrenBuilder
import react.Component
import react.State
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span

class PokemonListPage(props: PageRProps<PokemonListModel, Unit>) :
    Component<PageRProps<PokemonListModel, Unit>, State>(props) {

    override fun render() = renderPage(Page.POKEMON_LIST) {
        div {
            css(BasicStylesheet.widgetWrapper) {}
            div {
                css(BasicStylesheet.Table.table) {}
                div {
                    css(
                        BasicStylesheet.Table.row,
                        BasicStylesheet.Table.header
                    ) {}
                    div {
                        css(
                            BasicStylesheet.Table.cell,
                            BasicStylesheet.Table.headerCell,
                            BasicStylesheet.Table.first
                        ) {}
                        +"№"
                    }
                    div {
                        css(
                            BasicStylesheet.Table.cell,
                            BasicStylesheet.Table.headerCell
                        ) {}
                        +"name"
                    }
                    div {
                        css(
                            BasicStylesheet.Table.cell,
                            BasicStylesheet.Table.headerCell
                        ) {}
                        +"forms"
                    }
                }
                props.model.pokemon.forEach { pokemon ->
                    div {
                        css(BasicStylesheet.Table.row) {}
                        div {
                            css(
                                BasicStylesheet.Table.cell,
                                BasicStylesheet.Table.first,
                                BasicStylesheet.Table.left
                            ) {}
                            +"#${pokemon.pokedexNumber}"
                        }
                        div {
                            css(
                                BasicStylesheet.Table.cell,
                                Styles.pokemonName
                            ) {}
                            a {
                                href = pokemonPagePath(pokemon.pokedexNumber)
                                +pokemon.name
                            }
                        }
                        div {
                            css(BasicStylesheet.Table.cell) {}
                            pokemon.forms.forEach {
                                formWidget(pokemon.pokedexNumber, it)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun ChildrenBuilder.formWidget(pokedexNumber: Int, form: PokemonListModel.Form) {
        span {
            css(Styles.form) {}
            a {
                href = pokemonPagePath(pokedexNumber, form.toPokemonForm())
                +form.prettyNameOrDefault
            }
        }
    }

    private object Styles {
        val form = ClassName  {
            margin = StyleConstants.Margin.small
            fontWeight = FontWeight.bold
            fontSize = StyleConstants.Font.smaller
            display = Display.inlineBlock
            a {
                backgroundColor = StyleConstants.Colors.primary.bg
                color = StyleConstants.Colors.primary.text
                display = Display.inlineBlock
                borderRadius = 6.px
                padding = StyleConstants.Margin.small
                hover {
                    backgroundColor = StyleConstants.Colors.primaryHovered.bg
                    color = StyleConstants.Colors.primaryHovered.text
                    textDecoration = None.none
                }
            }
        }

        val pokemonName = ClassName {
            a {
                fontWeight = FontWeight.bold
            }
        }
    }
}