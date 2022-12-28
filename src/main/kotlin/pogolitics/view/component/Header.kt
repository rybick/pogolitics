package pogolitics.view.component

import kotlinx.css.*
import pogolitics.view.StyleConstants
import pogolitics.view.iconPath
import pogolitics.view.logoPath
import react.Props
import react.dom.attrs
import react.dom.img
import react.fc
import styled.StyleSheet
import styled.css
import styled.styledDiv

val Header = fc<HeaderProps> {
    styledDiv {
        css { +HeaderStyles.headerWrapper }
        img(src = logoPath()) {}
    }
}

interface HeaderProps: Props

private object HeaderStyles: StyleSheet("HeaderComponentStyles", isStatic = true) {
    val headerWrapper by css {
        boxSizing = BoxSizing.contentBox
        height = 70.px
        paddingTop = StyleConstants.Padding.small
        paddingBottom = StyleConstants.Padding.small
        paddingLeft = StyleConstants.Padding.medium
        paddingRight = StyleConstants.Padding.medium
        backgroundColor = StyleConstants.Colors.primary.bg
    }
}