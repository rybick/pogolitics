package pogolitics.view

import csstype.*
import pogolitics.cssClass

object BasicStylesheet {

    val widgetWrapper = cssClass {
        display = Display.block
        margin = StyleConstants.Margin.medium
    }

    val widgetHeader = cssClass {
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

    object Table {
        val table = cssClass {
            display = Display.table
            width = 100.pct
        }

        val cell = cssClass {
            display = Display.tableCell
            padding = StyleConstants.Padding.medium
            borderColor = StyleConstants.Colors.lightBorder
            borderStyle = LineStyle.solid
            borderBottomWidth = 1.px
            borderLeftWidth = 1.px
            borderTopWidth = 0.px
            borderRightWidth = 0.px
        }

        val headerCell = cssClass {
            // cursor = Cursor.pointer  // TODO later mig
        }

        val row = cssClass {
            display = Display.tableRow
            padding = StyleConstants.Padding.small
        }

        val header = cssClass {
            backgroundColor = StyleConstants.Colors.primary.bg
            color = StyleConstants.Colors.primary.text
            fontWeight = FontWeight.bold
        }

        val first = cssClass {
            borderLeftWidth = 0.px
        }

        val left = cssClass {
            textAlign = TextAlign.left
        }
    }
}