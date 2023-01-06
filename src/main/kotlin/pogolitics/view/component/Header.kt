package pogolitics.view.component

import csstype.BoxSizing
import csstype.Display
import csstype.number
import csstype.pct
import csstype.px
import emotion.css.ClassName
import emotion.react.css
import pogolitics.view.Page
import pogolitics.view.StyleConstants
import pogolitics.view.logoPath
import pogolitics.view.pagePath
import pogolitics.view.pokemonListPagePath
import react.FC
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img

val Header = FC<HeaderProps> { props ->

    div {
        css(HeaderStyles.headerWrapper) {}
        div {
            css(HeaderStyles.logoWrapper) {}
            a {
                href = pagePath(Page.HOME)
                img { src = logoPath() }
            }
        }
        div {
            css(HeaderStyles.searchInputWrapper) {}
            SearchBox {
                pokemonIndex = props.pokemonIndex
            }
        }
    }
}

interface HeaderProps: SearchBoxProps

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

    val searchInputWrapper = ClassName {
        margin = StyleConstants.Padding.big
        paddingRight = StyleConstants.Padding.medium
        paddingLeft = StyleConstants.Padding.big
    }
}