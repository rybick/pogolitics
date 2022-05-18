package pogolitics.view

import kotlinx.css.*
import kotlinx.html.title
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv

class StaticStatComponent: RComponent<StaticStatComponentRProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css { +Styles.wrapper }
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
                        attrs {
                            title = props.info
                        }
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

    private object Styles: StyleSheet("StaticStatComponentStyles", isStatic = true) {
        val wrapper by css {
            display = Display.inlineBlock
            textAlign = TextAlign.center
            marginRight = 1.px
        }
    }
}



external interface StaticStatComponentRProps: RProps {
    var name: String
    var value: String
    var info: String
}

fun RBuilder.staticStat(handler: StaticStatComponentRProps.() -> Unit): ReactElement {
    return child(StaticStatComponent::class) {
        this.attrs(handler)
    }
}