package pogolitics.controller

import org.w3c.dom.url.URLSearchParams
import pogolitics.ControllerResult
import pogolitics.PageRProps
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