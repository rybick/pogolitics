package pogolitcs.model

import kotlinx.css.Color

object Style {
    object Colors {
        val primary = ColorGroup(bg = "#0079fb", font = "#ffffff")
        val secondary = ColorGroup(bg = "#ffffff", font = "#000000")
        val lightBorder = Color("rgba(0, 60, 130, 0.25)")
    }

    object Padding {
        val small = "5px"
        val medium = "10px"
    }

    object Margin {
        val small = "5px"
    }

    class ColorGroup(val bg: Color, val font: Color) {
        constructor(bg: String, font: String): this(Color(bg), Color(font))
    }
}