package pogolitcs.view

import pogolitcs.PageRProps
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.p

class MainPage(props: PageRProps<Unit>) : RComponent<PageRProps<Unit>, RState>(props) {
    override fun RBuilder.render() {
        p { +"page under construction" }
    }
}