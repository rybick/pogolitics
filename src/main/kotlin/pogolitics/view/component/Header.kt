package pogolitics.view.component

import csstype.BoxSizing
import csstype.px
import emotion.css.ClassName
import emotion.react.css
import pogolitics.view.StyleConstants
import pogolitics.view.logoPath
import pogolitics.view.pokemonListPagePath
import react.Props
import react.dom.a
import react.dom.html.ReactHTML.div
import react.dom.img
import react.fc

val Header = fc<HeaderProps> {
    div {
        attrs.css(HeaderStyles.headerWrapper) {}
        a { // TODO lead to main page when there is anything there
            attrs.href = pokemonListPagePath()
            img { attrs.src = logoPath() }
        }
    }
}

interface HeaderProps: Props

private object HeaderStyles {
    val headerWrapper = ClassName {
        boxSizing = BoxSizing.contentBox
        height = 60.px
        paddingTop = StyleConstants.Padding.medium
        paddingBottom = StyleConstants.Padding.medium
        paddingLeft = StyleConstants.Padding.medium
        paddingRight = StyleConstants.Padding.medium
        backgroundColor = StyleConstants.Colors.primary.bg
    }
}