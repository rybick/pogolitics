package pogolitcs.view

import kotlinx.css.Color
import kotlinx.css.px

object StyleConstants {
    object Colors {
        val primary = ColorGroup(bg = "#0079fb", text = "#ffffff", secondaryText = "#777777")
        val secondary = ColorGroup(bg = "#ffffff", text = "#000000", secondaryText = "#aaaaaa")
        val lightBorder = Color("rgba(0, 60, 130, 0.25)")
    }

    object Padding {
        val small = 5.px
        val medium = 10.px
        val big = 20.px
    }

    object Margin {
        val small = 5.px
        val medium = 10.px
        val big = 20.px
    }

    object Border {
        val thick = 5.px
    }

    class ColorGroup(val bg: Color, val text: Color, val secondaryText: Color) {
        constructor(bg: String, text: String, secondaryText: String):
            this(Color(bg), Color(text), Color(secondaryText))
    }
}