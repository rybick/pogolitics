package pogolitics.view

import csstype.Color
import csstype.pct
import csstype.px


object StyleConstants {
    object Colors {
        val primary = ColorGroup(bg = "#0079fb", text = "#ffffff", secondaryText = "#dce8fe")
        val primaryHovered = ColorGroup(bg = "#5099ff", text = "#ffffff", secondaryText = "#ffffff")
        val secondary = ColorGroup(bg = "#ffffff", text = "#000000", secondaryText = "#aaaaaa")
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