package pogolitics

import browser.document
import csstype.Position
import csstype.PropertiesBuilder
import dom.Element
import react.RProps
import react.createElement
import emotion.react.css
import pogolitics.view.StyleConstants
import pogolitics.view.logoPath
import pogolitics.view.pokemonListPagePath
import react.*
import react.dom.client.createRoot
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img

fun main() {
    exportForJs()
    document.createElement("div")
        .apply { id = "root" }
        .also { document.body.appendChild(it) }
        .also { element: Element ->
            createRoot(element)
                .render(
                    createElement<RProps> {
                        child(App::class) {}
                    }
                )
        }
}

//val Cc = fc<HeaderProps> {
//    styledDiv { +"gav" }
//}

val Hc = fc<RProps> {
    div {
        attrs.css(HeaderStyles.headerWrapper)
        a {
            attrs.href = pokemonListPagePath()
            img { attrs.src = logoPath() }
        }
    }
}

val Dd = fc<Props> {
    div {
        attrs.css {}
    }
}


private object HeaderStyles {
    val headerWrapper: PropertiesBuilder.() -> Unit = {
        position = Position.absolute
        marginTop = StyleConstants.Margin.big
    }
}

fun exportForJs() {
    eval("window.PGL = {}")
    eval("window.PGL.CpCalculator = CpCalculator")
    eval("window.PGL.calcStatValue = calcStatValue")
}
