package pogolitics

import react.Component
import react.Props
import react.State
import kotlin.reflect.KClass

interface ControllerResult {
    val model: Any?
    val view: View?
    val isModelAndView: Boolean
    val notFoundReason: String?

    companion object {
        fun <M, V: TypedView<M>> modelAndView(model: M, view: V): ControllerResult  {
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

    private class TypedControllerResult<M, out V: TypedView<out M>>(
        override val model: M?,
        override val view: V?,
        override val isModelAndView: Boolean,
        override val notFoundReason: String?
    ): ControllerResult
}

private typealias TypedView<M> = KClass<out Component<out PageRProps<M, *>, out State>>

typealias View = TypedView<out Any?>

external interface PageRProps<M, S> : Props {
    var model: M
    var updateState: (S) -> Unit
}
