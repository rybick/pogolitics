package pogolitcs.view

import kotlinx.css.*
import styled.StyleSheet

object BasicStylesheet: StyleSheet("ComponentStyles", isStatic = true) {

    val widgetWrapper by css {
        display = Display.block
        margin = StyleConstants.margin.medium.toString()
    }

    val widgetHeader by css {
        paddingTop = StyleConstants.padding.medium
        paddingLeft = StyleConstants.padding.medium
        paddingBottom = StyleConstants.padding.medium
        paddingRight = StyleConstants.padding.medium
        backgroundColor = StyleConstants.colors.primary.bg
        color = StyleConstants.colors.primary.font
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
            padding = StyleConstants.padding.medium.toString()
            borderColor = StyleConstants.colors.lightBorder
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
            padding = StyleConstants.padding.small.toString()
        }

        val header by css {
            backgroundColor = StyleConstants.colors.primary.bg
            color = StyleConstants.colors.primary.font
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