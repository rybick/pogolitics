package pogolitics.view

import pogolitics.view.component.Breadcrumbs
import pogolitics.view.component.Header
import react.ChildrenBuilder
import react.Fragment
import react.ReactNode
import react.create

fun renderPage(page: Page?, contentRenderer: ChildrenBuilder.() -> Unit): ReactNode = Fragment.create {
    Header {}
    Breadcrumbs { this.page = page }
    contentRenderer()
}