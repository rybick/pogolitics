package pogolitcs

import react.RComponent
import react.RProps
import react.RState
import kotlin.reflect.KClass

data class ModelAndView<
    M,
    out V: KClass<out RComponent<out PageRProps<M>, out RState>>
>(
    val model: M,
    val view: V
)

interface PageRProps<T> : RProps {
    var model: T
}
