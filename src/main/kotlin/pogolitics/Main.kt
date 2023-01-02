package pogolitics

import browser.document
import dom.Element
import react.Fragment
import react.create
import react.dom.client.createRoot
import react.react

fun main() {
    exportForJs()
    document.createElement("div")
        .apply { id = "root" }
        .also { document.body.appendChild(it) }
        .also { element: Element ->
            createRoot(element)
                .render(
                    Fragment.create {
                        App::class.react { }
                    }
                )
        }
}

fun exportForJs() {
    eval("window.PGL = {}")
    eval("window.PGL.CpCalculator = CpCalculator")
    eval("window.PGL.calcStatValue = calcStatValue")
}
