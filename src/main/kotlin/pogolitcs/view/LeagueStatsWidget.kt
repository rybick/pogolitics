package pogolitcs.view

import kotlinx.css.*
import pogolitcs.model.PokemonIndividualValuesState
import pogolitcs.model.SinglePokemonModel
import react.*
import react.dom.span
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledSpan

class LeagueStatsWidget(props: LeagueStatsWidgetRProps) : RComponent<LeagueStatsWidgetRProps, RState>(props) {

    override fun RBuilder.render() {
        styledDiv {
            css { +BasicStylesheet.widgetWrapper }
            styledDiv {
                css {
                    + BasicStylesheet.widgetHeader
                }
                + (props.name + " league")
            }
            styledDiv {
                styledSpan {
                    css { + Styles.group }
                    styledSpan {
                        css { + Styles.label }
                        +"CP: "
                    }
                    styledSpan {
                        css { + Styles.value }
                        +"${props.stats.cp}"
                    }
                }
                styledSpan {
                    css { + Styles.group }
                    styledSpan {
                        css { + Styles.label }
                        +"level: "
                    }
                    styledSpan {
                        css { + Styles.value }
                        +"${props.stats.level}"
                    }
                }
            }
        }
    }

    private object Styles: StyleSheet("ComponentStyles", isStatic = true) {
        val label by css {
            + cell
            fontWeight = FontWeight.bold
        }

        val value by css {
            + cell
        }

        val group by css {
            marginLeft = StyleConstants.margin.big
            // display = Display.tableRow
        }

        val cell by css {
            padding = StyleConstants.padding.small.toString()
            display = Display.tableCell
        }
    }
}

external interface LeagueStatsWidgetRProps: RProps {
    var name: String;
    var stats: SinglePokemonModel.VariablePokemonStatistics;
    var onChange: (PokemonIndividualValuesState) -> Unit
}

fun RBuilder.leagueStatsWidget(handler: LeagueStatsWidgetRProps.() -> Unit): ReactElement {
    return child(LeagueStatsWidget::class) {
        this.attrs(handler)
    }
}