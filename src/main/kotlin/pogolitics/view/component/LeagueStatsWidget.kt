package pogolitics.view.component

import csstype.*
import emotion.react.css
import pogolitics.cssClass
import pogolitics.format
import pogolitics.model.SinglePokemonModel
import pogolitics.plus
import pogolitics.view.BasicStylesheet
import pogolitics.view.StyleConstants
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span

val LeagueStatsWidget = fc<LeagueStatsWidgetRProps> { props ->
    div {
        attrs.css(BasicStylesheet.widgetWrapper)
        div {
            attrs.css(BasicStylesheet.widgetHeader)
            + (props.name + " league")
        }
        div {
            attrs.onClick = { props.onClick() }
            attrs.css(LeagueStatsWidgetStyles.contentWrapper)
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

private fun RElementBuilder<*>.stat(label: String, value: String) {
    span {
        attrs.css(LeagueStatsWidgetStyles.group)
        span {
            attrs.css(LeagueStatsWidgetStyles.label)
            +"$label: "
        }
        span {
            attrs.css(LeagueStatsWidgetStyles.value)
            +value
        }
    }
}

private object LeagueStatsWidgetStyles {
    val cell = cssClass {
        padding = StyleConstants.Padding.small
        display = Display.tableCell
    }

    val label = cell + cssClass {
        fontWeight = FontWeight.bold
    }

    val value = cell + cssClass {}

    val group = cssClass {
        marginLeft = StyleConstants.Margin.big
    }

    val contentWrapper = cssClass {
        cursor = Cursor.pointer
        display = Display.flex
        flexWrap = FlexWrap.wrap
        flexDirection = FlexDirection.row
        justifyContent = JustifyContent.center
    }

}

