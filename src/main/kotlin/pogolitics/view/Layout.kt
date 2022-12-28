package pogolitics.view

import pogolitics.view.component.Header
import react.RBuilder

fun RBuilder.renderPage(contentRenderer: RBuilder.() -> Unit) {
    Header {}
    contentRenderer()
}