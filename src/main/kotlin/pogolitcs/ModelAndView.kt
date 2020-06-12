package pogolitcs

import react.RComponent
import react.RProps
import react.RState
import kotlin.reflect.KClass

data class ModelAndView<
    M,
    out V: KClass<out RComponent<out PageRProps<M, *>, out RState>> // TODO any?
>(
    val model: M,
    val view: V
)

interface PageRProps<M, S> : RProps {
    var model: M
    var state: S
    var updateState: (S) -> Unit
}

interface PageRState<T> : RState {
    var data: T? // TODO -?
}
