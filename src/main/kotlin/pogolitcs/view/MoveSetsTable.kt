package pogolitcs.view

import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import kotlinx.html.unsafe
import pogolitcs.model.MoveSet
import pogolitcs.format
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import styled.StyleSheet
import styled.css
import styled.styledDiv
import react.*

class MovesetsTable(props: MovesetsRProps) : RComponent<MovesetsRProps, MovesetsRState>(props) {
    override fun MovesetsRState.init(props: MovesetsRProps) {
        sort = null
    }

    private val UP_ICON = "&#9652;"
    private val DOWN_ICON = "&#9662;"
    private val SPACE = "&nbsp;"

    override fun RBuilder.render() {
        styledDiv {
            css { +Styles.wrapper }
            styledDiv {
                css { +Styles.table }
                styledDiv {
                    css {
                        +Styles.row
                        +Styles.header
                    }
                    styledDiv {
                        css {
                            +Styles.cell
                            +Styles.headerCell
                            +Styles.first
                        }
                        +"Moveset"
                    }
                    styledDiv {
                        css {
                            +Styles.cell
                            +Styles.headerCell
                        }
                        attrs {
                            onClickFunction = {
                                setState {
                                    val sort = this.sort
                                    this.sort = Sort(
                                            columnId = 1,
                                            ascending = if (sort?.columnId == 1) !sort.ascending else false
                                    )
                                }
                            }
                            unsafe { +("DPS" + getIcon(state.sort, 1)) }
                        }
                    }
                    styledDiv {
                        css {
                            +Styles.cell
                            +Styles.headerCell
                        }
                        attrs {
                            onClickFunction = {
                                setState {
                                    val sort = this.sort
                                    this.sort = Sort(
                                            columnId = 2,
                                            ascending = if (sort?.columnId == 2) !sort.ascending else false
                                    )
                                }
                            }
                            unsafe { +("TTFA" + getIcon(state.sort, 2)) }
                        }
                    }
                }
                sortValues(props.values, state.sort).forEach {
                    styledDiv {
                        css { +Styles.row }
                        styledDiv {
                            css {
                                +Styles.cell
                                +Styles.first
                                +Styles.left
                            }
                            child(AttackComponent::class) { attrs.attack = it.quickAttack }
                            +" + "
                            child(AttackComponent::class) { attrs.attack = it.chargedAttack }
                        }
                        styledDiv {
                            css { +Styles.cell }
                            +it.dps.format(2)
                        }
                        styledDiv {
                            css { +Styles.cell }
                            +(it.timeToFirstAttack.inSeconds.format(2) + "s")
                        }
                    }
                }
            }
        }
    }

    // TODO later make it prettier and more generic
    private fun sortValues(values: List<MoveSet>, sort: Sort?): List<MoveSet> {
        return when(sort?.columnId) {
            1 -> values.sortedBy { -it.dps * sort.ascendFactor }
            2 -> values.sortedBy { -it.timeToFirstAttack * sort.ascendFactor }
            else -> values
        }
    }

    private fun getIcon(sort: Sort?, columnId: Int): Any? {
        if (sort != null && sort.columnId == columnId) {
            return if (sort.ascending) DOWN_ICON else UP_ICON
        } else {
            return SPACE
        }
    }

    private object Styles: StyleSheet("ComponentStyles", isStatic = true) {
        val cell by css {
            display = Display.tableCell
            padding = Style.padding.medium.toString()
            borderColor = Style.colors.lightBorder
            borderStyle = BorderStyle.solid
            borderBottomWidth = LinearDimension("1px")
            borderLeftWidth = LinearDimension("1px")
            borderTopWidth = LinearDimension("0px")
            borderRightWidth = LinearDimension("0px")
        }

        val headerCell by css {
            cursor = Cursor.pointer
        }

        val row by css {
            display = Display.tableRow
            padding = Style.padding.small.toString()
        }

        val header by css {
            backgroundColor = Style.colors.primary.bg
            color = Style.colors.primary.font
            fontWeight = FontWeight.bold
        }

        val table by css {
            display = Display.table
            width = 100.pct
        }

        val wrapper by css {
            display = Display.block
            margin = Style.margin.small.toString()
        }

        val first by css {
            borderLeftWidth = LinearDimension("0px")
        }

        val left by css {
            textAlign = TextAlign.left
        }
    }
}

fun RBuilder.moveSetsTable(handler: MovesetsRProps.() -> Unit): ReactElement {
    return child(MovesetsTable::class) {
        this.attrs(handler)
    }
}

external interface MovesetsRProps: RProps {
    var values: List<MoveSet>
}

external interface MovesetsRState: RState {
    var sort: Sort?
}

data class Sort(val columnId: Int, val ascending: Boolean) {
    val ascendFactor get() = if (ascending) 1 else -1
}


