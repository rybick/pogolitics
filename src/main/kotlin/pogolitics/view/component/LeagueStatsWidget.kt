package pogolitics.view.component

import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import pogolitics.format
import pogolitics.model.SinglePokemonModel
import pogolitics.view.BasicStylesheet
import pogolitics.view.StyleConstants
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledSpan

val LeagueStatsWidget = fc<LeagueStatsWidgetRProps> { props ->
    styledDiv {
        css { +BasicStylesheet.widgetWrapper }
        styledDiv {
            css {
                +BasicStylesheet.widgetHeader
            }
            + (props.name + " league")
        }
        styledDiv {
            attrs.onClickFunction = { props.onClick() }
            css {
                +LeagueStatsWidgetStyles.contentWrapper
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

external interface LeagueStatsWidgetRProps: RProps {
    var name: String;
    var stats: SinglePokemonModel.VariablePokemonStatistics;
    var onClick: () -> Unit
}

private fun RBuilder.stat(label: String, value: String) {
    styledSpan {
        css { +LeagueStatsWidgetStyles.group }
        styledSpan {
            css { +LeagueStatsWidgetStyles.label }
            +"$label: "
        }
        styledSpan {
            css { +LeagueStatsWidgetStyles.value }
            +value
        }
    }
}

private object LeagueStatsWidgetStyles: StyleSheet("LeagueStatsWidgetStyles", isStatic = true) {
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

