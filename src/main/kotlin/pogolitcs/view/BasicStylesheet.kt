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
}