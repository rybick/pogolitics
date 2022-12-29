package styled

import react.RElementBuilder
import react.dom.html.HTMLAttributes
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.span

fun RElementBuilder<HTMLAttributes<*>>.styledImg(src: String, block: HtmlBlock) =
    img {
        attrs.src = src
        block(this)
    }

fun RElementBuilder<HTMLAttributes<*>>.styledA(href: String, block: HtmlBlock) =
    a {
        attrs.href = href
        block(this)
    }

fun RElementBuilder<HTMLAttributes<*>>.styledSpan(block: HtmlBlock) = span(block)
fun RElementBuilder<HTMLAttributes<*>>.styledDiv(block: HtmlBlock) = div(block)

typealias HtmlBlock = RElementBuilder<HTMLAttributes<*>>.() -> Unit