package pogolitics.view

import react.RBuilder

fun RBuilder.renderPage(contentRenderer: RBuilder.() -> Unit) {
    contentRenderer()
}