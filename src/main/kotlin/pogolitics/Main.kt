package pogolitics

import react.Fragment
import react.create
import react.dom.client.createRoot
import react.react
import web.dom.Element
import web.dom.document

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
