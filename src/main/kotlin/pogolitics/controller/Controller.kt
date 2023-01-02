package pogolitics.controller

import org.w3c.dom.url.URLSearchParams
import pogolitics.ControllerResult
import pogolitics.PageRProps
import react.RComponent
import react.State
import react.router.Params
import kotlin.reflect.KClass

interface Controller<M, S> {
    fun getInitialState(url: String): S
    suspend fun get(
        props: Params,
        params: URLSearchParams,
        state: S
    ): ControllerResult<M, KClass<out RComponent<out PageRProps<M, S>, out State>>>
}