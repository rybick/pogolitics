package pogolitics.view

import pogolitics.view.component.Breadcrumbs
import pogolitics.view.component.Header
import react.RBuilder

fun RBuilder.renderPage(page: Page?, contentRenderer: RBuilder.() -> Unit) {
    Header {}
    Breadcrumbs { attrs.page = page }
    contentRenderer()
}