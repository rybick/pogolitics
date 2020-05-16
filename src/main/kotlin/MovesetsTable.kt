import kotlinx.css.*
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import styled.StyleSheet
import styled.css
import styled.styledDiv
import kotlin.Float

class MovesetsTable: RComponent<MovesetsRProps, RState>() {
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
                        +Styles.first
                    }
                    +"Moveset"
                }
                styledDiv {
                    css { +Styles.cell }
                    +"DPS"
                }
                styledDiv {
                    css { +Styles.cell }
                    +"TTFA"
                }
            }
            props.values.forEach {
                styledDiv {
                    css { +Styles.row }
                    styledDiv {
                        css {
                            +Styles.cell
                            +Styles.first
                        }
                        child(AttackComponent::class) { attrs.attack = it.fastAttack }
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

external interface MovesetsRProps: RProps {
    var values: List<Moveset>
}

data class Moveset(
        val fastAttack: Attack,
        val chargedAttack: Attack,
        val dps: Float,
        val timeToFirstAttack: Float
)

