package pogolitics.view

import pogolitics.PageRProps
import pogolitics.model.PokemonListModel
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.a
import styled.css
import styled.styledDiv

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
                        +"form"
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
                            }
                            a(href = pokemonPagePath(pokemon.pokedexNumber, pokemon.form)) {
                                +pokemon.name
                            }
                        }
                        styledDiv {
                            css {
                                +BasicStylesheet.Table.cell
                            }
                            +(pokemon.form ?: "")
                        }
                    }

                }
            }
        }
    }
}