import react.dom.ul
import react.dom.li
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState

class ElementList: RComponent<ElementListRProps, RState>() {
    override fun RBuilder.render() {
        ul {
            props.elements.forEach {
                li { +it }
            }
        }
    }
}

external interface ElementListRProps: RProps {
    var elements: List<String>
}