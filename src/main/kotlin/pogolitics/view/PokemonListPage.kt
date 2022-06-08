package pogolitics.view

import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import pogolitics.PageRProps
import pogolitics.model.PokemonListModel
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.a
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledSpan

class PokemonListPage(props: PageRProps<PokemonListModel, Unit>) :
    RComponent<PageRProps<PokemonListModel, Unit>, RState>(props) {
    override fun RBuilder.render() {
        styledDiv {
            css { +BasicStylesheet.widgetWrapper }
            styledDiv {
                css { +BasicStylesheet.Table.table }
                styledDiv {
                    css {
                        +BasicStylesheet.Table.row
                        +BasicStylesheet.Table.header
                    }
                    styledDiv {
                        css {
                            +BasicStylesheet.Table.cell
                            +BasicStylesheet.Table.headerCell
                            +BasicStylesheet.Table.first
                        }
                        +"№"
                    }
                    styledDiv {
                        css {
                            +BasicStylesheet.Table.cell
                            +BasicStylesheet.Table.headerCell
                        }
                        +"name"
                    }
                    styledDiv {
                        css {
                            +BasicStylesheet.Table.cell
                            +BasicStylesheet.Table.headerCell
                        }
                        +"forms"
                    }
                }
                props.model.pokemon.forEach { pokemon ->
                    styledDiv {
                        css { +BasicStylesheet.Table.row }
                        styledDiv {
                            css {
                                +BasicStylesheet.Table.cell
                                +BasicStylesheet.Table.first
                                +BasicStylesheet.Table.left
                            }
                            +"${pokemon.pokedexNumber}"
                        }
                        styledDiv {
                            css {
                                +BasicStylesheet.Table.cell
                                +Styles.pokemonName
                            }
                            a(href = pokemonPagePath(pokemon.pokedexNumber)) {
                                +pokemon.name
                            }
                        }
                        styledDiv {
                            css {
                                +BasicStylesheet.Table.cell
                            }
                            //+(pokemon.form ?: "")
                            pokemon.forms.forEach {
                                formWidget(pokemon.pokedexNumber, it)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun RBuilder.formWidget(pokedexNumber: Int, form: PokemonListModel.Form) {
        styledSpan {
            css {
                +Styles.form
            }
            a(href = pokemonPagePath(pokedexNumber, form.name)) {
                +form.prettyName
            }
        }
    }

    private object Styles: StyleSheet("PokemonListPageStyles", isStatic = true) {
        val form by css {
            margin = "${StyleConstants.Margin.small}"
            fontWeight = FontWeight.bold
            fontSize = StyleConstants.Font.smaller
            display = Display.inlineBlock
            a {
                backgroundColor = StyleConstants.Colors.primary.bg
                color = StyleConstants.Colors.primary.text
                display = Display.inlineBlock
                borderRadius = 6.px
                padding = "${StyleConstants.Margin.small}"
                hover {
                    backgroundColor = StyleConstants.Colors.primaryHovered.bg
                    color = StyleConstants.Colors.primaryHovered.text
                    textDecoration = TextDecoration.none
                }
            }
        }

        val pokemonName by css {
            a {
                fontWeight = FontWeight.bold
            }
        }
    }
}