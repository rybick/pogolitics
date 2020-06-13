package pogolitcs.view

import kotlinx.css.*
import kotlinx.css.properties.scale
import kotlinx.css.properties.transform
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.RProps
import react.ReactElement
import react.dom.defaultValue
import react.dom.input
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledInput

class IVBarComponent(props: IVBarComponentRProps) : RComponent<IVBarComponentRProps, MovesetsRState>(props) {
    private val styles = Styles(1.px)

    private val MAX_IV = 15

    override fun RBuilder.render() {
        styledDiv {
            css {
                +styles.componentWrapper
            }
            styledDiv {
                css {
                    +styles.labelsWrapper
                }
                styledDiv {
                    css {
                        +styles.label
                        if (props.iv == MAX_IV) {
                            +styles.labelIvMax
                        }
                    }
                    +props.name
                }
                styledDiv {
                    css {
                        +styles.inputWrapper
                    }
                    styledInput(InputType.number) {
                        css {
                            +styles.input
                        }
                        attrs.min = "0"
                        attrs.max = "15"
                        attrs.pattern = "\\d*"
                        attrs.defaultValue = props.iv.toString()
                        attrs.onChangeFunction = {
                            props.onChange((it.target as HTMLInputElement).value.toInt())
                        }
                    }
                }
            }
            styledDiv {
                css {
                    +styles.wrapper
                }
                styledDiv {
                    css {
                        +styles.bar
                        +styles.bg
                        width = styles.barWidth(MAX_IV)
                    }
                }
                styledDiv {
                    css {
                        +styles.bar
                        +styles.content
                        width = styles.barWidth(props.iv)
                        if (props.iv == 0) {
                            +styles.iv0
                        } else if (props.iv == MAX_IV) {
                            +styles.ivMax
                        }
                    }
                }
                styledDiv {
                    css {
                        +styles.scale
                    }
                }
            }
        }
    }

    private class Styles(val unit: LinearDimension): StyleSheet("ComponentStyles", isStatic = false) {
        private val regularColor = Color("#f0911d")
        private val strongColor = Color("#e18077")
        private val gray = Color("#e2e2e2")

        private val Number.u: LinearDimension get() = unit * this

        fun barWidth(width: Int): LinearDimension {
            return 20.u * (width - 1)
        }

        val componentWrapper by css {
            width = 320.u
            paddingLeft = 6.u
            paddingRight = 6.u
        }

        val labelsWrapper by css {
            display = Display.table
            width = 100.pct
        }

        val label by css {
            display = Display.tableCell
            paddingLeft = 5.u
            paddingTop = 20.u
            fontWeight = FontWeight("600")
            color = regularColor
        }

        val inputWrapper by css {
            display = Display.tableCell
            textAlign = TextAlign.right
        }

        val input by css {
            textAlign = TextAlign.right
            maxHeight = 20.u
            fontSize = 80.pct
        }

        val wrapper by css {
            marginBottom = 20.u
            backgroundColor = Color.white
        }

        val bar by css {
            height = 20.u
            backgroundColor = regularColor
            marginLeft = 10.u
            marginRight = 10.u
            before {
                content = QuotedString("")
                marginLeft = (-8).u
                position = Position.absolute
                float = Float.left
                height = 20.u
                width = 10.u
                backgroundColor = regularColor
                borderTopLeftRadius = 10.u
                borderBottomLeftRadius = 10.u
                transform {
                    scale(0.8, 1.0)
                }
            }
            after {
                content = QuotedString("")
                marginRight = (-8).u
                float = Float.right
                height = 20.u
                width = 10.u
                backgroundColor = regularColor
                borderTopRightRadius = 10.u
                borderBottomRightRadius = 10.u
                transform {
                    scale(0.8, 1.0)
                }
            }
        }

        val bg by css {
            backgroundColor = gray
            marginBottom = (-20).u
            before {
                backgroundColor = gray
            }
            after {
                backgroundColor = gray
            }
        }

        val content by css {
            position = Position.absolute
        }

        val scale by css {
            marginTop = (-20).u
            paddingBottom = (-20).u
            width = 150.u
            before {
                position = Position.absolute
                content = QuotedString("")
                marginLeft = 100.u
                width = 4.u
                height = 20.u
                backgroundColor = Color.white
                display = Display.block
            }
            after {
                position = Position.absolute
                content = QuotedString("")
                marginLeft = 200.u
                width = 4.u
                height = 20.u
                backgroundColor = Color.white
                display = Display.block
            }
        }

        val iv0 by css {
            before {
                display = Display.none
            }
            after {
                display = Display.none
            }
        }

        val ivMax by css {
            backgroundColor = strongColor
            before {
                backgroundColor = strongColor
            }
            after {
                backgroundColor = strongColor
            }
        }

        val labelIvMax by css {
            color = strongColor
        }

    }
}

external interface IVBarComponentRProps: RProps {
    var name: String
    var iv: Int
    var onChange: (Int) -> Unit
}

fun RBuilder.ivBar(handler: IVBarComponentRProps.() -> Unit): ReactElement {
    return child(IVBarComponent::class) {
        this.attrs(handler)
    }
}