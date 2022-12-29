package pogolitics

import react.dom.*
import kotlinx.browser.document
import org.w3c.dom.Element
import react.dom.client.createRoot

fun main() {
    exportForJs()
    document.createElement("div")
        .apply { id = "root" }
        .also { document.body!!.appendChild(it) }
        .also { element: Element ->
            //createRoot(element as dom.Element)//.render(App::class)
            render(element as dom.Element) {
                child(App::class) {}
            }
        }
}

fun exportForJs() {
    eval("window.PGL = {}")
    eval("window.PGL.CpCalculator = CpCalculator")
    eval("window.PGL.calcStatValue = calcStatValue")
}
