package pogolitics.view

import csstype.Border
import csstype.Color
import csstype.LineStyle
import csstype.pct
import csstype.px


object StyleConstants {
    object Colors {
        private val primaryAccent = "#f0911d" //"#0079fb"
        private val hoveredPrimary = "#e18077" // "#5099ff"
        private val lightPrimary = "#fee8dc" // in between textPrimary and primaryAccent

        private val white = "#ffffff"
        private val black = "#000000"

        private val textPrimary = white
        private val textPrimarySoft = lightPrimary

        private val none = white // not expected to be used

        val primary = ColorGroup(bg = primaryAccent, text = textPrimary, secondaryText = textPrimarySoft)
        val primaryHovered = ColorGroup(bg = hoveredPrimary, text = textPrimary, secondaryText = textPrimary)
        val secondary = ColorGroup(bg = white, text = black, secondaryText = "#aaaaaa")
        val secondarySpecial = ColorGroup(bg = "#ededed", text = "#000000", secondaryText = "#999999")
        val primaryInactive = secondarySpecial
        val secondaryLink = ColorGroup(bg = white, text = primaryAccent, secondaryText = none)
        val secondaryLinkHovered = ColorGroup(bg = white, text = hoveredPrimary, secondaryText = none)
        val lightBorder = Color(lightPrimary) //Color("rgba(130, 60, 0, 0.25)")
    }

    object Padding {
        val small = 5.px
        val medium = 10.px
        val semiBig = 15.px
        val big = 20.px
        val huge = 40.px
    }

    object Margin {
        val small = 5.px
        val medium = 10.px
        val semiBig = 15.px
        val big = 20.px
        val huge = 40.px
    }

    object Border {
        val thick = 5.px
        object Radius {
            val small = 5.px
            val medium = 10.px
            val big = 20.px
        }

        val thickBorder = Border(thick, LineStyle.solid, Colors.primary.bg)
    }

    object Font {
        val bigger = 110.pct
        val smaller = 90.pct
        val small = 80.pct
        val h3 = 120.pct
    }

    class ColorGroup(val bg: Color, val text: Color, val secondaryText: Color) {
        constructor(bg: String, text: String, secondaryText: String):
            this(Color(bg), Color(text), Color(secondaryText))
    }
}