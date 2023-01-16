package pogolitics

import react.Component
import react.Props
import react.State
import kotlin.reflect.KClass

interface ControllerResult {
    val model: Any?
    val view: Any?
    val isModelAndView: Boolean
    val notFoundReason: String?

    companion object {
        fun <M, V: View<M>> modelAndView(model: M, view: V): ControllerResult  {
            return TypedControllerResult(
                model = model,
                view = view,
                isModelAndView = true,
                notFoundReason = null
            )
        }

        fun notFound(reason: String): ControllerResult {
            return TypedControllerResult(
                model = null,
                view = null,
                isModelAndView = false,
                notFoundReason = reason
            )
        }
    }

    private class TypedControllerResult<M, out V: View<M>>(
        override val model: M?,
        override val view: V?,
        override val isModelAndView: Boolean,
        override val notFoundReason: String?
    ): ControllerResult
}

private typealias View <M> = KClass<out Component<out PageRProps<M, *>, out State>>

interface PageRProps<M, S> : Props {
    var model: M
    var updateState: (S) -> Unit
}
