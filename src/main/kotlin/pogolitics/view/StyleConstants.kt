package pogolitics.view

import csstype.Color
import csstype.pct
import csstype.px


object StyleConstants {
    object Colors {
        private val primaryAccent = "#0079fb" //"#f0911d"
        private val hoveredPrimary = "#5099ff" // "#e18077"

        private val white = "#ffffff"
        private val black = "#000000"

        private val textPrimary = white
        private val textPrimarySoft = "#dce8fe" // in between textPrimary and primaryAccent

        val primary = ColorGroup(bg = primaryAccent, text = textPrimary, secondaryText = textPrimarySoft)
        val primaryHovered = ColorGroup(bg = hoveredPrimary, text = textPrimary, secondaryText = textPrimary)
        val secondary = ColorGroup(bg = white, text = black, secondaryText = "#aaaaaa")
        val secondarySpecial = ColorGroup(bg = "#ededed", text = "#000000", secondaryText = "#999999")
        val lightBorder = Color("rgba(0, 60, 130, 0.25)")
    }

    object Padding {
        val small = 5.px
        val medium = 10.px
        val semiBig = 15.px
        val big = 20.px
    }

    object Margin {
        val small = 5.px
        val medium = 10.px
        val big = 20.px
    }

    object Border {
        val thick = 5.px
        object Radius {
            val small = 5.px
            val medium = 10.px
            val big = 20.px
        }
    }

    object Font {
        val bigger = 110.pct
        val smaller = 90.pct
        val small = 80.pct
    }

    class ColorGroup(val bg: Color, val text: Color, val secondaryText: Color) {
        constructor(bg: String, text: String, secondaryText: String):
            this(Color(bg), Color(text), Color(secondaryText))
    }
}