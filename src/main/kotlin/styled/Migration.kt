package styled

import react.ChildrenBuilder
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.span

fun ChildrenBuilder.styledImg(src: String, block: HtmlBlock) =
    img {
        this.src = src
        block(this)
    }

fun ChildrenBuilder.styledA(href: String, block: HtmlBlock) =
    a {
        this.href = href
        block(this)
    }

fun ChildrenBuilder.styledSpan(block: HtmlBlock) = span(block)
fun ChildrenBuilder.styledDiv(block: HtmlBlock) = div(block)

typealias HtmlBlock = ChildrenBuilder.() -> Unit // RElementBuilder<HTMLAttributes<*>>.() -> Unit