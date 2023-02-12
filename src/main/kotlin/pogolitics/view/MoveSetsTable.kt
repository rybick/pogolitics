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
                        css(Table.cell, Table.headerCell, Table.sortable) {}
                        onClick = { changeSort(1) }
                        +("DPS" + getIcon(state.sort, 1))
                        title = "Damage per second"
                    }
                    div {
                        css(Table.cell, Table.headerCell, Table.sortable) {}
                        onClick = { changeSort(2) }
                        +("TTFA" + getIcon(state.sort, 2))
                        title = "Time to first (charged) attack"
                    }
                    div {
                        css(Table.cell, Table.headerCell, Table.sortable) {}
                        onClick = { changeSort(3) }
                        +("MTBA" + getIcon(state.sort, 3))
                        title = "Mean time between (charged) attacks"
                    }
                    div {
                        css(Table.cell, Table.headerCell, Table.sortable) {}
                        onClick = { changeSort(4) }
                        +("FDPS" + getIcon(state.sort, 4))
                        title = "DPS when using only fast attack"
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
                        div {
                            css(Table.cell) {}
                            +it.fastAttackDps.format(2)
                        }
                    }
                }
            }
        }
    }

    private fun changeSort(columnId: Int) {
        setState({ state ->
            val sort = state.sort
            state.sort = Sort(
                columnId = columnId,
                ascending = if (sort?.columnId == columnId) !sort.ascending else false
            )
            state
        })
    }

    private fun sortValues(values: List<MoveSet>, sort: Sort?): List<MoveSet> {
        fun sortBy(valueProducer: (MoveSet) -> Float) = values.sortedBy { -valueProducer(it) * sort!!.ascendFactor }

        return when(sort?.columnId) {
            1 -> sortBy { it.dps }
            2 -> sortBy { it.timeToFirstAttack.inWholeMilliseconds.toFloat() }
            3 -> sortBy { it.meanTimeBetweenAttacks.inWholeMilliseconds.toFloat() }
            4 -> sortBy { it.fastAttackDps }
            else -> values
        }
    }

    private fun getIcon(sort: Sort?, columnId: Int): Char =
        if (sort != null && sort.columnId == columnId) {
            if (sort.ascending) UP_ICON else DOWN_ICON
        } else {
            SPACE
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
    val ascendFactor get() = if (ascending) -1 else +1
}



