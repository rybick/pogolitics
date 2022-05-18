package pogolitics.view

import kotlinx.css.*
import styled.StyleSheet

object BasicStylesheet: StyleSheet("ComponentStyles", isStatic = true) {

    val widgetWrapper by css {
        display = Display.block
        margin = StyleConstants.Margin.medium.toString()
    }

    val widgetHeader by css {
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

    object Table: StyleSheet("TableStyles", isStatic = true) {
        val table by css {
            display = Display.table
            width = 100.pct
        }

        val cell by css {
            display = Display.tableCell
            padding = StyleConstants.Padding.medium.toString()
            borderColor = StyleConstants.Colors.lightBorder
            borderStyle = BorderStyle.solid
            borderBottomWidth = LinearDimension("1px")
            borderLeftWidth = LinearDimension("1px")
            borderTopWidth = LinearDimension("0px")
            borderRightWidth = LinearDimension("0px")
        }

        val headerCell by css {
            cursor = Cursor.pointer
        }

        val row by css {
            display = Display.tableRow
            padding = StyleConstants.Padding.small.toString()
        }

        val header by css {
            backgroundColor = StyleConstants.Colors.primary.bg
            color = StyleConstants.Colors.primary.text
            fontWeight = FontWeight.bold
        }

        val first by css {
            borderLeftWidth = LinearDimension("0px")
        }

        val left by css {
            textAlign = TextAlign.left
        }
    }
}