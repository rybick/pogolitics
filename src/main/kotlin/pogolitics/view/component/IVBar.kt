package pogolitics.view.component

import csstype.*
import dom.html.HTMLDivElement
import emotion.css.ClassName
import emotion.react.css
import pogolitics.pointer
import react.*
import react.dom.events.MouseEvent
import react.dom.events.MouseEventHandler
import react.dom.html.InputType
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import kotlin.math.round

val IVBar = FC<IVBarComponentRProps> { props ->
    val styles = IVBarStyles(1.px)

    val MAX_IV = 15

    // TODO later, check the thing with always redrawing when onClickFunction (or other) is defined inline

    val doOnClick: MouseEventHandler<HTMLDivElement> = { mouseEvent: MouseEvent<HTMLDivElement, *> ->
        val target: HTMLDivElement = mouseEvent.target as HTMLDivElement
        val x: Double = mouseEvent.clientX - target.getBoundingClientRect().left
        val barWidth: Double = target.getBoundingClientRect().width
        val ratio = x / barWidth
        val value = 0.5 + ratio * (MAX_IV - 0.5)
        props.onChange(round(value).toInt())
    }

    div {
        css(styles.componentWrapper) {}
        div {
            css(styles.labelsWrapper) {}
            div {
                css(
                    styles.label,
                    if (props.iv == MAX_IV) {
                        styles.labelIvMax
                    } else styles.none
                ) {}
                +props.name
            }
            div {
                css(styles.inputWrapper) {}
                input {
                    css(styles.input) {}
                    type = InputType.number
                    min = "0"
                    max = "15"
                    pattern = "\\d*"
                    key = "${props.iv}"
                    defaultValue = "${props.iv}"
                    onChange = {
                        props.onChange(it.target.value.toInt())
                    }
                }
            }
        }
        div {
            css(styles.wrapper) {}
            div {
                css(styles.bar, styles.bg) {
                    width = styles.barWidth(MAX_IV)
                }
                onClick = doOnClick
            }
            div {
                css(
                    styles.bar,
                    styles.content,
                    when (props.iv) {
                        0 ->  styles.iv0
                        MAX_IV -> styles.ivMax
                        else -> styles.none
                    }
                ) {
                    width = styles.barWidth(props.iv)
                }
            }
            div {
                css(styles.scale) {}
            }
        }
    }

}

external interface IVBarComponentRProps: Props {
    var name: String
    var iv: Int
    var onChange: (Int) -> Unit
}

class IVBarStyles(val unit: Length) {
    private val regularColor = Color("#f0911d")
    private val strongColor = Color("#e18077")
    private val gray = Color("#e2e2e2")

    private val Number.u: Length get() = "calc($unit * $this)".unsafeCast<Length>()

    fun barWidth(width: Int): Length {
        return (20 * (width - 1)).u
    }

    val componentWrapper = ClassName {
        width = 320.u
        paddingLeft = 6.u
        paddingRight = 6.u
        paddingTop = 10.u
        paddingBottom = 10.u
    }

    val labelsWrapper = ClassName {
        display = Display.flex
        flexDirection = FlexDirection.row
    }

    val label = ClassName {
        display = Display.flex
        flexDirection = FlexDirection.columnReverse
        paddingLeft = 5.u
        fontWeight = integer(600)
        color = regularColor
    }

    val inputWrapper = ClassName {
        flexGrow = number(1.0)
        paddingBottom = 3.u
        paddingRight = 10.u
        textAlign = TextAlign.right
    }

    val input = ClassName {
        textAlign = TextAlign.right
        maxHeight = 20.u
        fontSize = 80.pct
    }

    val wrapper = ClassName {
        marginBottom = 20.u
        backgroundColor = NamedColor.white
    }

    val bar = ClassName {
        height = 20.u
        backgroundColor = regularColor
        marginLeft = 10.u
        marginRight = 10.u
        cursor = pointer
        before {
            content = Content("")
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
            content = Content("")
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

    val bg = ClassName {
        backgroundColor = gray
        marginBottom = (-20).u
        before {
            backgroundColor = gray
        }
        after {
            backgroundColor = gray
        }
    }

    val content = ClassName {
        position = Position.absolute
        pointerEvents = None.none
    }

    val scale = ClassName {
        marginTop = (-20).u
        paddingBottom = (-20).u
        width = 150.u
        before {
            position = Position.absolute
            content = Content("")
            marginLeft = 100.u
            width = 4.u
            height = 20.u
            backgroundColor = NamedColor.white
            display = Display.block
        }
        after {
            position = Position.absolute
            content = Content("")
            marginLeft = 200.u
            width = 4.u
            height = 20.u
            backgroundColor = NamedColor.white
            display = Display.block
        }
    }

    val iv0 = ClassName {
        before {
            display = None.none
        }
        after {
            display = None.none
        }
    }

    val ivMax = ClassName {
        backgroundColor = strongColor
        before {
            backgroundColor = strongColor
        }
        after {
            backgroundColor = strongColor
        }
    }

    val labelIvMax = ClassName {
        color = strongColor
    }

    val none = ClassName { }

}

