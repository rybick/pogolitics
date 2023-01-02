package pogolitics.view.component

import csstype.BoxSizing
import csstype.Display
import csstype.number
import csstype.px
import emotion.css.ClassName
import emotion.react.css
import pogolitics.view.StyleConstants
import pogolitics.view.logoPath
import pogolitics.view.pokemonListPagePath
import react.FC
import react.Props
import react.dom.html.ReactHTML.a
import react.SearchInput
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img

val Header = FC<HeaderProps> {
    div {
        css(HeaderStyles.headerWrapper) {}
        div {
            css(HeaderStyles.logoWrapper) {}
            a { // TODO lead to main page when there is anything there
                href = pokemonListPagePath()
                img { src = logoPath() }
            }
        }
        div {
            css(HeaderStyles.searchInputWrapper) {}
            SearchInput {}
        }
    }
}

interface HeaderProps: Props

private object HeaderStyles {
    val headerWrapper = ClassName {
        display = Display.flex
        boxSizing = BoxSizing.contentBox
        height = 60.px
        paddingTop = StyleConstants.Padding.medium
        paddingBottom = StyleConstants.Padding.medium
        paddingLeft = StyleConstants.Padding.medium
        paddingRight = StyleConstants.Padding.medium
        backgroundColor = StyleConstants.Colors.primary.bg
    }

    val logoWrapper = ClassName {
        flex = number(1.0)
    }

    val searchInputWrapper = ClassName {}
}