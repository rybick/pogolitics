package pogolitics.view

import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import pogolitics.format
import pogolitics.model.SinglePokemonModel
import react.*
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
                attrs.onClickFunction = { props.onClick() }
                css {
                    + Styles.contentWrapper
                }
                stat("CP", "${props.stats.cp}")
                stat("level", "${props.stats.level}")
                stat("hardiness", props.stats.hardiness.format(2))
                stat("atk", props.stats.attack.format(2))
                stat("def", props.stats.defense.format(2))
                stat("sta", props.stats.stamina.format(2))
            }
        }
    }

    private fun RBuilder.stat(label: String, value: String) {
        styledSpan {
            css { + Styles.group }
            styledSpan {
                css { + Styles.label }
                +"$label: "
            }
            styledSpan {
                css { + Styles.value }
                +value
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
            marginLeft = StyleConstants.Margin.big
        }

        val contentWrapper by css {
            cursor = Cursor.pointer
            display = Display.flex
            flexWrap = FlexWrap.wrap
            flexDirection = FlexDirection.row
            justifyContent = JustifyContent.center
        }

        val cell by css {
            padding = StyleConstants.Padding.small.toString()
            display = Display.tableCell
        }
    }
}

external interface LeagueStatsWidgetRProps: RProps {
    var name: String;
    var stats: SinglePokemonModel.VariablePokemonStatistics;
    var onClick: () -> Unit
}

fun RBuilder.leagueStatsWidget(handler: LeagueStatsWidgetRProps.() -> Unit): ReactElement {
    return child(LeagueStatsWidget::class) {
        this.attrs(handler)
    }
}