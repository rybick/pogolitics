import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import kotlinx.html.unsafe
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import styled.StyleSheet
import styled.css
import styled.styledDiv
import kotlin.Float
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
                        unsafe { + ("DPS" + getIcon(state.sort, 1)) }
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
                        +(it.timeToFirstAttack.format(2) + "s")
                    }
                }
            }
        }
    }

    // TODO later make it prettier and more generic
    private fun sortValues(values: List<Moveset>, sort: Sort?): List<Moveset> {
        return when(sort?.columnId) {
            1 -> values.sortedBy { -it.dps * sort.ascendFactor }
            2 -> values.sortedBy { -it.timeToFirstAttack * sort.ascendFactor }
            else -> values
        }
    }

    private fun getIcon(sort: Sort?, columnId: Int): Any? {
        if (sort != null && sort.columnId == columnId) {
            return if (sort.ascending) UP_ICON else DOWN_ICON
        } else {
            return SPACE
        }
    }

    private object Styles: StyleSheet("ComponentStyles", isStatic = true) {
        val cell by css {
            display = Display.tableCell
            padding = Style.Padding.medium
            borderColor = Style.Colors.lightBorder
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
            padding = Style.Padding.small
        }

        val header by css {
            backgroundColor = Style.Colors.primary.bg
            color = Style.Colors.primary.font
            fontWeight = FontWeight.bold
        }

        val table by css {
            display = Display.table
            margin = Style.Margin.small
        }

        val first by css {
            borderLeftWidth = LinearDimension("0px")
        }
    }
}

fun RBuilder.movesetsTable(handler: MovesetsRProps.() -> Unit): ReactElement {
    return child(MovesetsTable::class) {
        this.attrs(handler)
    }
}

external interface MovesetsRProps: RProps {
    var values: List<Moveset>
}

external interface MovesetsRState: RState {
    var values: List<Moveset>
    var sort: Sort?
}

data class Sort(val columnId: Int, val ascending: Boolean) {
    val ascendFactor get() = if (ascending) 1 else -1
}

data class Moveset(
    val quickAttack: Attack,
    val chargedAttack: Attack,
    val dps: Float,
    val timeToFirstAttack: Float
)

