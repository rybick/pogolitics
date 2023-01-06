package pogolitics.view

import emotion.react.css
import js.core.jso
import pogolitics.model.MoveSet
import pogolitics.format
import pogolitics.view.BasicStylesheet.Table
import pogolitics.view.component.Attack
import react.*
import react.dom.html.ReactHTML.div
import kotlin.text.Typography.nbsp
import kotlin.time.DurationUnit

class MovesetsTable(props: MovesetsRProps) : Component<MovesetsRProps, MovesetsRState>(props) {

    init {
        state = jso {
            sort = null
        }
    }

    private val UP_ICON = Char(9652) //"&#9652;"
    private val DOWN_ICON = Char(9662) //"&#9662;"
    private val SPACE = nbsp //"&nbsp;"

    override fun render() = Fragment.create {
        div {
            css(BasicStylesheet.widgetWrapper) {}
            div {
                css(Table.table) {}
                div {
                    css(Table.row, Table.header) {}
                    div {
                        css(Table.cell, Table.headerCell, Table.first) {}
                        +"Moveset"
                    }
                    div {
                        css(Table.cell, Table.headerCell) {}
                        onClick = {
                            setState({ state ->
                                val sort = state.sort
                                state.sort = Sort(
                                        columnId = 1,
                                        ascending = if (sort?.columnId == 1) !sort.ascending else false
                                )
                                state
                            })
                        }
                        +("DPS" + getIcon(state.sort, 1))
                        title = "Damage per second"
                    }
                    div {
                        css(Table.cell, Table.headerCell) {}
                        onClick = {
                            setState({ state ->
                                val sort = state.sort
                                state.sort = Sort(
                                        columnId = 2,
                                        ascending = if (sort?.columnId == 2) !sort.ascending else false
                                )
                                state
                            })
                        }
                        +("TTFA" + getIcon(state.sort, 2))
                        title = "Time to first (charged) attack"
                    }
                    div {
                        css(Table.cell, Table.headerCell) {}
                        onClick = {
                            setState({ state ->
                                val sort = state.sort
                                state.sort = Sort(
                                    columnId = 3,
                                    ascending = if (sort?.columnId == 3) !sort.ascending else false
                                )
                                state
                            })
                        }
                        +("MTBA" + getIcon(state.sort, 3))
                        title = "Mean time between (charged) attacks"
                    }
                }
                sortValues(props.values, state.sort).forEach {
                    div {
                        css(Table.row) {}
                        div {
                            css(Table.cell, Table.first, Table.left) {}
                            Attack { attack = it.quickAttack }
                            +" + "
                            Attack { attack = it.chargedAttack }
                        }
                        div {
                            css(Table.cell) {}
                            +it.dps.format(2)
                        }
                        div {
                            css(Table.cell) {}
                            +(it.timeToFirstAttack.toDouble(DurationUnit.SECONDS).format(2) + "s")
                        }
                        div {
                            css(Table.cell) {}
                            +(it.meanTimeBetweenAttacks.toDouble(DurationUnit.SECONDS).format(2) + "s")
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
}

fun ChildrenBuilder.moveSetsTable(handler: MovesetsRProps.() -> Unit): Unit =
    MovesetsTable::class.react(handler)

external interface MovesetsRProps: Props {
    var values: List<MoveSet>
}

external interface MovesetsRState: State {
    var sort: Sort?
}

data class Sort(val columnId: Int, val ascending: Boolean) {
    val ascendFactor get() = if (ascending) 1 else -1
}



