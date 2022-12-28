package pogolitics.view

import kotlinx.html.js.onClickFunction
import kotlinx.html.unsafe
import pogolitics.model.MoveSet
import pogolitics.format
import pogolitics.view.BasicStylesheet.Table
import pogolitics.view.component.Attack
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import styled.css
import styled.styledDiv
import react.*
import kotlin.time.DurationUnit

class MovesetsTable(props: MovesetsRProps) : RComponent<MovesetsRProps, MovesetsRState>(props) {
    override fun MovesetsRState.init(props: MovesetsRProps) {
        sort = null
    }

    private val UP_ICON = "&#9652;"
    private val DOWN_ICON = "&#9662;"
    private val SPACE = "&nbsp;"

    override fun RBuilder.render() {
        styledDiv {
            css { +BasicStylesheet.widgetWrapper }
            styledDiv {
                css { +Table.table }
                styledDiv {
                    css {
                        +Table.row
                        +Table.header
                    }
                    styledDiv {
                        css {
                            +Table.cell
                            +Table.headerCell
                            +Table.first
                        }
                        +"Moveset"
                    }
                    styledDiv {
                        css {
                            +Table.cell
                            +Table.headerCell
                        }
                        attrs.onClickFunction = {
                                setState {
                                    val sort = this.sort
                                    this.sort = Sort(
                                            columnId = 1,
                                            ascending = if (sort?.columnId == 1) !sort.ascending else false
                                    )
                                }
                            }
                        attrs.unsafe { +("DPS" + getIcon(state.sort, 1)) }
                    }
                    styledDiv {
                        css {
                            +Table.cell
                            +Table.headerCell
                        }
                        attrs.onClickFunction = {
                                setState {
                                    val sort = this.sort
                                    this.sort = Sort(
                                            columnId = 2,
                                            ascending = if (sort?.columnId == 2) !sort.ascending else false
                                    )
                                }
                            }
                        attrs.unsafe { +("TTFA" + getIcon(state.sort, 2)) }
                    }
                    styledDiv {
                        css {
                            +Table.cell
                            +Table.headerCell
                        }
                            attrs.onClickFunction = {
                                setState {
                                    val sort = this.sort
                                    this.sort = Sort(
                                        columnId = 3,
                                        ascending = if (sort?.columnId == 3) !sort.ascending else false
                                    )
                                }
                            }
                        attrs.unsafe { +("MTBA" + getIcon(state.sort, 3)) }
                    }
                }
                sortValues(props.values, state.sort).forEach {
                    styledDiv {
                        css { +Table.row }
                        styledDiv {
                            css {
                                +Table.cell
                                +Table.first
                                +Table.left
                            }
                            Attack { attrs.attack = it.quickAttack }
                            +" + "
                            Attack { attrs.attack = it.chargedAttack }
                        }
                        styledDiv {
                            css { +Table.cell }
                            +it.dps.format(2)
                        }
                        styledDiv {
                            css { +Table.cell }
                            +(it.timeToFirstAttack.toDouble(DurationUnit.SECONDS).format(2) + "s")
                        }
                        styledDiv {
                            css { +Table.cell }
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



