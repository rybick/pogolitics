package pogolitcs

import react.dom.*
import kotlin.browser.document

fun main() {
    exportForJs()
    document.createElement("div")
        .apply { id = "root" }
        .also { document.body!!.appendChild(it) }
        .also {
            render(it) {
                child(App::class) {}
            }
        }
}

fun exportForJs() {
    eval("window.PGL = {}")
    eval("window.PGL.CpCalculator = CpCalculator")
    eval("window.PGL.calcStatValue = calcStatValue")
}
