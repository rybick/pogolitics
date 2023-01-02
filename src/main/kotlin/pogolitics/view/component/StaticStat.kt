package pogolitics.view.component

import csstype.Display
import csstype.TextAlign
import csstype.px
import emotion.css.ClassName
import emotion.react.css
import pogolitics.view.BasicStylesheet
import react.*
import react.dom.html.ReactHTML.div

val StaticStat = fc<StaticStatComponentRProps>() { props ->
    div {
        attrs.css(StaticStatStyles.wrapper) {}
        div {
            attrs.css(BasicStylesheet.Table.table) {}
            div {
                attrs.css(BasicStylesheet.Table.row, BasicStylesheet.Table.header) {}
                div {
                    attrs.css(
                        BasicStylesheet.Table.cell,
                        BasicStylesheet.Table.headerCell,
                        BasicStylesheet.Table.first
                    ) {}
                    attrs.title = props.info
                    +props.name
                }
            }
            div {
                attrs.css(BasicStylesheet.Table.row) {}
                div {
                    attrs.css(
                        BasicStylesheet.Table.cell,
                        BasicStylesheet.Table.headerCell,
                        BasicStylesheet.Table.first
                    ) {}
                    +props.value
                }
            }
        }
    }
}

external interface StaticStatComponentRProps: Props {
    var name: String
    var value: String
    var info: String
}

private object StaticStatStyles {
    val wrapper = ClassName {
        display = Display.inlineBlock
        textAlign = TextAlign.center
        marginRight = 1.px
    }
}