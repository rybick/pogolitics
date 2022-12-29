package pogolitics.view.component

import kotlinx.css.*
import kotlinx.html.title
import pogolitics.view.BasicStylesheet
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv

val StaticStat = fc<StaticStatComponentRProps>() { props ->
    styledDiv {
        css { +StaticStatStyles.wrapper }
        styledDiv {
            css { +BasicStylesheet.Table.table }
            styledDiv {
                css {
                    +BasicStylesheet.Table.row
                    +BasicStylesheet.Table.header
                }
                styledDiv {
                    css {
                        +BasicStylesheet.Table.cell
                        +BasicStylesheet.Table.headerCell
                        +BasicStylesheet.Table.first
                    }
                    attrs.title = props.info
                    +props.name
                }
            }
            styledDiv {
                css {
                    +BasicStylesheet.Table.row
                }
                styledDiv {
                    css {
                        +BasicStylesheet.Table.cell
                        +BasicStylesheet.Table.headerCell
                        +BasicStylesheet.Table.first
                    }
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

private object StaticStatStyles: StyleSheet("StaticStatComponentStyles", isStatic = true) {
    val wrapper by css {
        display = Display.inlineBlock
        textAlign = TextAlign.center
        marginRight = 1.px
    }
}