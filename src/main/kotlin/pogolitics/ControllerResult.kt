package pogolitics

import react.RComponent
import react.RProps
import react.RState
import kotlin.reflect.KClass

class ControllerResult<
    M,
    out V: View<M>
> private constructor(
    val model: M?,
    val view: V?,
    val isModelAndView: Boolean,
    val notFoundReason: String?
) {
    companion object {
        fun <M, V: View<M>> modelAndView(model: M, view: V): ControllerResult<M, V>  {
            return ControllerResult(
                model = model,
                view = view,
                isModelAndView = true,
                notFoundReason = null
            )
        }

        fun <M, V: View<M>> notFound(reason: String): ControllerResult<M, V> {
            return ControllerResult(
                model = null,
                view = null,
                isModelAndView = false,
                notFoundReason = reason
            )
        }
    }
}

typealias View <M> = KClass<out RComponent<out PageRProps<M, *>, out RState>>

interface PageRProps<M, S> : RProps {
    var model: M
    var updateState: (S) -> Unit
}

interface PageRState<T> : RState {
    var data: T?
}