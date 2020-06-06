package view

import PageRProps
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.p

class MainPage(props: PageRProps<Unit>) : RComponent<PageRProps<Unit>, RState>(props) {
    override fun RBuilder.render() {
        p { +"page under construction" }
    }
}