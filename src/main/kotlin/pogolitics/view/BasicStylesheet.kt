package pogolitics.view

import csstype.Display
import csstype.FontWeight
import csstype.LineStyle
import csstype.TextAlign
import csstype.TextTransform
import csstype.pct
import csstype.px
import emotion.css.ClassName
import pogolitics.pointer

object BasicStylesheet {

    val widgetWrapper = ClassName {
        display = Display.block
        margin = StyleConstants.Margin.medium
    }

    val widgetHeader = ClassName {
        paddingTop = StyleConstants.Padding.medium
        paddingLeft = StyleConstants.Padding.medium
        paddingBottom = StyleConstants.Padding.medium
        paddingRight = StyleConstants.Padding.medium
        backgroundColor = StyleConstants.Colors.primary.bg
        color = StyleConstants.Colors.primary.text
        fontWeight = FontWeight.bold
        textAlign = TextAlign.center
        textTransform = TextTransform.capitalize;
    }

    val clickablePrimary = ClassName {
        cursor = pointer
        hover {
            color = StyleConstants.Colors.primaryHovered.text
            backgroundColor = StyleConstants.Colors.primaryHovered.bg
        }
    }

    val clickableWidgetHeader = ClassName(widgetHeader, clickablePrimary) {}

    object Table {
        val table = ClassName {
            display = Display.table
            width = 100.pct
        }

        val cell = ClassName {
            display = Display.tableCell
            padding = StyleConstants.Padding.medium
            borderColor = StyleConstants.Colors.lightBorder
            borderStyle = LineStyle.solid
            borderBottomWidth = 1.px
            borderLeftWidth = 1.px
            borderTopWidth = 0.px
            borderRightWidth = 0.px
        }

        val headerCell = ClassName {

        }

        val row = ClassName {
            display = Display.tableRow
            padding = StyleConstants.Padding.small
        }

        val header = ClassName {
            backgroundColor = StyleConstants.Colors.primary.bg
            color = StyleConstants.Colors.primary.text
            fontWeight = FontWeight.bold
        }

        val first = ClassName {
            borderLeftWidth = 0.px
        }

        val left = ClassName {
            textAlign = TextAlign.left
        }

        val sortable = ClassName(clickablePrimary) {}
    }
}