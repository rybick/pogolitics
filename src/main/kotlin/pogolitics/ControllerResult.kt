package pogolitics

import react.Component
import react.Props
import react.State
import kotlin.reflect.KClass

typealias LooselyTypedControllerResult = ControllerResult<*, *>

class WControllerResult {

}

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
        fun <M, V: View<M>> modelAndView(model: M, view: V): ControllerResult<*, *>  {
            return ControllerResult(
                model = model,
                view = view,
                isModelAndView = true,
                notFoundReason = null
            )
        }

        fun notFound(reason: String): ControllerResult<*, *> {
            return ControllerResult(
                model = null,
                view = null,
                isModelAndView = false,
                notFoundReason = reason
            )
        }
    }
}

typealias View <M> = KClass<out Component<out PageRProps<M, *>, out State>>

interface PageRProps<M, S> : Props {
    var model: M
    var updateState: (S) -> Unit
}

interface PageRState<T> : State {
    var data: T?
}
