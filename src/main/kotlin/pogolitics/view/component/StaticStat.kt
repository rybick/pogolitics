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
        css(StaticStatStyles.wrapper) {}
        div {
            css(BasicStylesheet.Table.table) {}
            div {
                css(BasicStylesheet.Table.row, BasicStylesheet.Table.header) {}
                div {
                    css(
                        BasicStylesheet.Table.cell,
                        BasicStylesheet.Table.headerCell,
                        BasicStylesheet.Table.first
                    ) {}
                    title = props.info
                    +props.name
                }
            }
            div {
                css(BasicStylesheet.Table.row) {}
                div {
                    css(
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