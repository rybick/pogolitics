package pogolitics

import dom.Element
import kotlinx.browser.document
import react.RProps
import react.createElement
import react.dom.client.createRoot

fun main() {
    exportForJs()
    document.createElement("div")
        .apply { id = "root" }
        .also { document.body!!.appendChild(it) }
        // don't know why the types don't match here.
        // It's the same js class but ported two kotlin twice. Both libs are the newest version.
        .let { it as Element }
        .also { element: Element ->
            createRoot(element)
                .render(
                    createElement<RProps> {
                        child(App::class) {}
                    }
                )
        }
}

fun exportForJs() {
    eval("window.PGL = {}")
    eval("window.PGL.CpCalculator = CpCalculator")
    eval("window.PGL.calcStatValue = calcStatValue")
}
