package pogolitics.view.component

import csstype.*
import dom.html.HTMLDivElement
import emotion.react.css
import pogolitics.cssClass
import pogolitics.plus
import react.*
import react.dom.events.MouseEvent
import react.dom.events.MouseEventHandler
import react.dom.html.InputType
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import styled.styledDiv
import kotlin.math.round

val IVBar = fc<IVBarComponentRProps> { props ->
    val styles = IVBarStyles(1.px)

    val MAX_IV = 15

    // TODO later, check the thing with always redrawing when onClickFunction (or other) is defined inline

    val doOnClick: MouseEventHandler<HTMLDivElement> = { event: MouseEvent<HTMLDivElement, *> ->
        val mouseEvent = event //.unsafeCast<MouseEvent>()
        val target: HTMLDivElement = event.target as HTMLDivElement // TODO later mig - does it work?
        val x: Double = mouseEvent.clientX - target.getBoundingClientRect().left
        val barWidth: Double = target.getBoundingClientRect().width
        val ratio = x / barWidth
        val value = 0.5 + ratio * (MAX_IV - 0.5)
        props.onChange(round(value).toInt())
    }

    div {
        attrs.css(styles.componentWrapper)
        styledDiv {
            attrs.css(styles.labelsWrapper)
            styledDiv {
                attrs.css(
                    styles.label +
                        if (props.iv == MAX_IV) {
                            styles.labelIvMax
                        } else ({})
                )
                +props.name
            }
            styledDiv {
                attrs.css(styles.inputWrapper)
                input {
                    attrs.css(styles.input)
                    attrs.type = InputType.number
                    attrs.min = "0"
                    attrs.max = "15"
                    attrs.pattern = "\\d*"
                    attrs.key = "${props.iv}"
                    attrs.defaultValue = "${props.iv}"
                    attrs.onChange = {
                        props.onChange(it.target.value.toInt())
                    }
                }
            }
        }
        styledDiv {
            attrs.css(styles.wrapper)
            div {
                attrs.css(
                    styles.bar +
                    styles.bg +
                    ({ width = styles.barWidth(MAX_IV) })
                )
                attrs.onClick = doOnClick
            }
            styledDiv {
                attrs.css(
                    styles.bar +
                    styles.content +
                    (
                        if (props.iv == 0) {
                            styles.iv0
                        } else if (props.iv == MAX_IV) {
                            styles.ivMax
                        } else ({})
                    ) + ({ width = styles.barWidth(props.iv) })
                )
            }
            styledDiv {
                attrs.css(styles.scale)
            }
        }
    }

}

external interface IVBarComponentRProps: RProps {
    var name: String
    var iv: Int
    var onChange: (Int) -> Unit
}

class IVBarStyles(val unit: Length) {
    private val regularColor = Color("#f0911d")
    private val strongColor = Color("#e18077")
    private val gray = Color("#e2e2e2")

    private val Number.u: Length get() = "calc($unit * $this)".unsafeCast<Length>() // TODO later mig does it work?

    fun barWidth(width: Int): Length {
        return (20 * (width - 1)).u
    }

    val componentWrapper = cssClass {
        width = 320.u
        paddingLeft = 6.u
        paddingRight = 6.u
        paddingTop = 10.u
        paddingBottom = 10.u
    }

    val labelsWrapper = cssClass {
        display = Display.flex
        flexDirection = FlexDirection.row
    }

    val label = cssClass {
        display = Display.flex
        flexDirection = FlexDirection.columnReverse
        paddingLeft = 5.u
        fontWeight = integer(600)
        color = regularColor
    }

    val inputWrapper = cssClass {
        flexGrow = number(1.0)
        paddingBottom = 3.u
        paddingRight = 10.u
        textAlign = TextAlign.right
    }

    val input = cssClass {
        textAlign = TextAlign.right
        maxHeight = 20.u
        fontSize = 80.pct
    }

    val wrapper = cssClass {
        marginBottom = 20.u
        backgroundColor = NamedColor.white
    }

    val bar = cssClass {
        height = 20.u
        backgroundColor = regularColor
        marginLeft = 10.u
        marginRight = 10.u
        // cursor = Cursor.pointer // TODO later mig
        before {
            content = string("") // TODO later mig QuotedString?
            marginLeft = (-8).u
            position = Position.absolute
            float = Float.left
            height = 20.u
            width = 10.u
            backgroundColor = regularColor
            borderTopLeftRadius = 10.u
            borderBottomLeftRadius = 10.u
            transform = scale(0.8, 1.0)
        }
        after {
            content = string("")
            marginRight = (-8).u
            float = Float.right
            height = 20.u
            width = 10.u
            backgroundColor = regularColor
            borderTopRightRadius = 10.u
            borderBottomRightRadius = 10.u
            transform = scale(0.8, 1.0)
        }
    }

    val bg = cssClass {
        backgroundColor = gray
        marginBottom = (-20).u
        before {
            backgroundColor = gray
        }
        after {
            backgroundColor = gray
        }
    }

    val content = cssClass {
        position = Position.absolute
        pointerEvents = None.none
    }

    val scale = cssClass {
        marginTop = (-20).u
        paddingBottom = (-20).u
        width = 150.u
        before {
            position = Position.absolute
            content = string("")
            marginLeft = 100.u
            width = 4.u
            height = 20.u
            backgroundColor = NamedColor.white
            display = Display.block
        }
        after {
            position = Position.absolute
            content = string("")
            marginLeft = 200.u
            width = 4.u
            height = 20.u
            backgroundColor = NamedColor.white
            display = Display.block
        }
    }

    val iv0 = cssClass {
        before {
            display = None.none
        }
        after {
            display = None.none
        }
    }

    val ivMax = cssClass {
        backgroundColor = strongColor
        before {
            backgroundColor = strongColor
        }
        after {
            backgroundColor = strongColor
        }
    }

    val labelIvMax = cssClass {
        color = strongColor
    }

}

