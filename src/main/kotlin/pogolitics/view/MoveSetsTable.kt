package pogolitics.view

import emotion.react.css
import pogolitics.model.MoveSet
import pogolitics.format
import pogolitics.view.BasicStylesheet.Table
import pogolitics.view.component.Attack
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.*
import react.dom.html.ReactHTML.div
import kotlin.text.Typography.nbsp
import kotlin.time.DurationUnit

class MovesetsTable(props: MovesetsRProps) : RComponent<MovesetsRProps, MovesetsRState>(props) {
    override fun MovesetsRState.init(props: MovesetsRProps) {
        sort = null
    }

    private val UP_ICON = Char(9652) //"&#9652;"
    private val DOWN_ICON = Char(9662) //"&#9662;"
    private val SPACE = nbsp //"&nbsp;"

    override fun RBuilder.render() {
        div {
            attrs.css(BasicStylesheet.widgetWrapper) {}
            div {
                attrs.css(Table.table) {}
                div {
                    attrs.css(Table.row, Table.header) {}
                    div {
                        attrs.css(Table.cell, Table.headerCell, Table.first) {}
                        +"Moveset"
                    }
                    div {
                        attrs.css(Table.cell, Table.headerCell) {}
                        attrs.onClick = {
                            setState {
                                val sort = this.sort
                                this.sort = Sort(
                                        columnId = 1,
                                        ascending = if (sort?.columnId == 1) !sort.ascending else false
                                )
                            }
                        }
                        +("DPS" + getIcon(state.sort, 1))
                    }
                    div {
                        attrs.css(Table.cell, Table.headerCell) {}
                        attrs.onClick = {
                            setState {
                                val sort = this.sort
                                this.sort = Sort(
                                        columnId = 2,
                                        ascending = if (sort?.columnId == 2) !sort.ascending else false
                                )
                            }
                        }
                        +("TTFA" + getIcon(state.sort, 2))
                    }
                    div {
                        attrs.css(Table.cell, Table.headerCell) {}
                        attrs.onClick = {
                            setState {
                                val sort = this.sort
                                this.sort = Sort(
                                    columnId = 3,
                                    ascending = if (sort?.columnId == 3) !sort.ascending else false
                                )
                            }
                        }
                        +("MTBA" + getIcon(state.sort, 3))
                    }
                }
                sortValues(props.values, state.sort).forEach {
                    div {
                        attrs.css(Table.row) {}
                        div {
                            attrs.css(Table.cell, Table.first, Table.left) {}
                            Attack { attrs.attack = it.quickAttack }
                            +" + "
                            Attack { attrs.attack = it.chargedAttack }
                        }
                        div {
                            attrs.css(Table.cell) {}
                            +it.dps.format(2)
                        }
                        div {
                            attrs.css(Table.cell) {}
                            +(it.timeToFirstAttack.toDouble(DurationUnit.SECONDS).format(2) + "s")
                        }
                        div {
                            attrs.css(Table.cell) {}
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

fun RBuilder.moveSetsTable(handler: MovesetsRProps.() -> Unit) {
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



