package pogolitics

import browser.document
import dom.Element
import react.RProps
import react.createElement
import react.dom.client.createRoot

fun main() {
    exportForJs()
    document.createElement("div")
        .apply { id = "root" }
        .also { document.body.appendChild(it) }
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
