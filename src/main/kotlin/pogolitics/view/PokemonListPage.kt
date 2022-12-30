package pogolitics.view

import csstype.*
import emotion.react.css
import pogolitics.PageRProps
import pogolitics.cssClass
import pogolitics.model.PokemonListModel
import pogolitics.plus
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.a
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span
import styled.styledDiv

class PokemonListPage(props: PageRProps<PokemonListModel, Unit>) :
    RComponent<PageRProps<PokemonListModel, Unit>, RState>(props) {

    override fun RBuilder.render() = renderPage(Page.POKEMON_LIST) {
        div {
            attrs.css(BasicStylesheet.widgetWrapper)
            styledDiv {
                attrs.css(BasicStylesheet.Table.table)
                styledDiv {
                    attrs.css(
                        BasicStylesheet.Table.row +
                        BasicStylesheet.Table.header
                    )
                    styledDiv {
                        attrs.css(
                            BasicStylesheet.Table.cell +
                            BasicStylesheet.Table.headerCell +
                            BasicStylesheet.Table.first
                        )
                        +"№"
                    }
                    styledDiv {
                        attrs.css(
                            BasicStylesheet.Table.cell +
                            BasicStylesheet.Table.headerCell
                        )
                        +"name"
                    }
                    styledDiv {
                        attrs.css(
                            BasicStylesheet.Table.cell +
                            BasicStylesheet.Table.headerCell
                        )
                        +"forms"
                    }
                }
                props.model.pokemon.forEach { pokemon ->
                    styledDiv {
                        attrs.css(BasicStylesheet.Table.row)
                        styledDiv {
                            attrs.css(
                                BasicStylesheet.Table.cell +
                                BasicStylesheet.Table.first +
                                BasicStylesheet.Table.left
                            )
                            +"#${pokemon.pokedexNumber}"
                        }
                        styledDiv {
                            attrs.css(
                                BasicStylesheet.Table.cell +
                                Styles.pokemonName
                            )
                            a(href = pokemonPagePath(pokemon.pokedexNumber)) {
                                +pokemon.name
                            }
                        }
                        styledDiv {
                            attrs.css(
                                BasicStylesheet.Table.cell
                            )
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
        span {
            attrs.css(Styles.form)
            a(href = pokemonPagePath(pokedexNumber, form.toPokemonForm())) {
                +form.prettyNameOrDefault
            }
        }
    }

    private object Styles {
        val form = cssClass  {
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

        val pokemonName = cssClass {
            a {
                fontWeight = FontWeight.bold
            }
        }
    }
}