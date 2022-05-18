package pogolitics.view

import pogolitics.PageRProps
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.p

class MainPage(props: PageRProps<Unit, Unit>) : RComponent<PageRProps<Unit, Unit>, RState>(props) {
    override fun RBuilder.render() {
        p { +"page under construction" }
    }
}