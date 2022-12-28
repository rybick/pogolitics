package pogolitics.view.component

import kotlinx.css.*
import pogolitics.view.StyleConstants
import pogolitics.view.logoPath
import pogolitics.view.pokemonListPagePath
import react.Props
import react.dom.a
import react.dom.img
import react.fc
import styled.StyleSheet
import styled.css
import styled.styledDiv

val Header = fc<HeaderProps> {
    styledDiv {
        css { +HeaderStyles.headerWrapper }
        a(href = pokemonListPagePath()) { // TODO lead to main page when there is anything there
            img(src = logoPath()) {}
        }
    }
}

interface HeaderProps: Props

private object HeaderStyles: StyleSheet("HeaderComponentStyles", isStatic = true) {
    val headerWrapper by css {
        boxSizing = BoxSizing.contentBox
        height = 60.px
        paddingTop = StyleConstants.Padding.medium
        paddingBottom = StyleConstants.Padding.medium
        paddingLeft = StyleConstants.Padding.medium
        paddingRight = StyleConstants.Padding.medium
        backgroundColor = StyleConstants.Colors.primary.bg
    }
}