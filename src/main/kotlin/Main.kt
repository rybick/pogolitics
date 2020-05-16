import react.dom.*
import kotlin.browser.document

fun main(args: Array<String>) {
    render(document.getElementById("root")) {
        child(App::class) {}
    }
}