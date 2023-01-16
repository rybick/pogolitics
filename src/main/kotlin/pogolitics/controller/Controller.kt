package pogolitics.controller

import org.w3c.dom.url.URLSearchParams
import pogolitics.ControllerResult
import pogolitics.PageRProps
import react.Component
import react.State
import react.router.Params
import kotlin.reflect.KClass

interface Controller<M, S> {
    fun getInitialState(url: String): S
    suspend fun get(
        props: Params,
        params: URLSearchParams,
        state: S
    ): ControllerResult<*, *>
}