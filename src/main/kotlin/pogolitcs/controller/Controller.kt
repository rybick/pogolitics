package pogolitcs.controller

import org.w3c.dom.url.URLSearchParams
import pogolitcs.ControllerResult
import pogolitcs.PageRProps
import react.RComponent
import react.RProps
import react.RState
import kotlin.reflect.KClass

interface Controller<P: RProps, M, S> {
    fun getInitialState(url: String): S
    suspend fun get(
        props: P,
        params: URLSearchParams,
        state: S
    ): ControllerResult<M, KClass<out RComponent<out PageRProps<M, S>, out RState>>>
}