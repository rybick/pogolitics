package pogolitics

import csstype.PropertiesBuilder

fun cssClass(block: CssBuilder): CssBuilder = block

typealias CssBuilder = PropertiesBuilder.() -> Unit

operator fun CssBuilder.plus(other: CssBuilder): CssBuilder = plus(this, other)

fun plus(block1: CssBuilder, block2: CssBuilder): CssBuilder = {
    block1(this)
    block2(this)
}
