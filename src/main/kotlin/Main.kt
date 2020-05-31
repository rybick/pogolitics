import react.dom.*
import kotlin.browser.document

fun main() {
    document.createElement("div")
        .apply { id = "root" }
        .also { document.body!!.appendChild(it) }
        .also {
            render(it) {
                child(App::class) {}
            }
        }
}
