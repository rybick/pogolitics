package pogolitcs.view

import kotlinx.css.Color
import kotlinx.css.LinearDimension
import kotlinx.css.px
import styled.StyledComponents.css

object StyleConstants {
    val colors = Colors(
        primary = ColorGroup(bg = "#0079fb", font = "#ffffff", secondaryFont = "#777777"),
        secondary = ColorGroup(bg = "#ffffff", font = "#000000", secondaryFont = "#aaaaaa"),
        lightBorder = Color("rgba(0, 60, 130, 0.25)")
    )
    val padding = Padding(small = 5.px, medium = 10.px, big = 20.px)
    val margin = Margin(small = 5.px, medium = 10.px, big = 20.px)
    // TODO make them into objects too

    object Border {
        val thick = 5.px
    }

    class Colors(val primary: ColorGroup, val secondary: ColorGroup, val lightBorder: Color)
    class Padding(val small: LinearDimension, val medium: LinearDimension, val big: LinearDimension)
    class Margin(val small: LinearDimension, val medium: LinearDimension, val big: LinearDimension)

    class ColorGroup(val bg: Color, val font: Color, val secondaryFont: Color) {
        constructor(bg: String, font: String, secondaryFont: String):
                this(Color(bg), Color(font), Color(secondaryFont))
    }
}