package pogolitics.view.component

import csstype.Display
import csstype.TextAlign
import csstype.px
import emotion.react.css
import pogolitics.cssClass
import pogolitics.plus
import pogolitics.view.BasicStylesheet
import react.*
import react.dom.html.ReactHTML.div
import styled.styledDiv

val StaticStat = fc<StaticStatComponentRProps>() { props ->
    div {
        attrs.css(StaticStatStyles.wrapper)
        styledDiv {
            attrs.css(BasicStylesheet.Table.table)
            styledDiv {
                attrs.css(
                    BasicStylesheet.Table.row + BasicStylesheet.Table.header
                )
                div {
                    attrs.css(
                        BasicStylesheet.Table.cell +
                        BasicStylesheet.Table.headerCell +
                        BasicStylesheet.Table.first
                    )
                    attrs.title = props.info
                    +props.name
                }
            }
            styledDiv {
                attrs.css(BasicStylesheet.Table.row)
                styledDiv {
                    attrs.css(
                        BasicStylesheet.Table.cell +
                        BasicStylesheet.Table.headerCell +
                        BasicStylesheet.Table.first
                    )
                    +props.value
                }
            }
        }
    }
}

external interface StaticStatComponentRProps: RProps {
    var name: String
    var value: String
    var info: String
}

private object StaticStatStyles {
    val wrapper = cssClass {
        display = Display.inlineBlock
        textAlign = TextAlign.center
        marginRight = 1.px
    }
}