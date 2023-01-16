package pogolitics.controller

import org.w3c.dom.url.URLSearchParams
import pogolitics.ControllerResult
import pogolitics.TypedControllerResult
import react.router.Params

interface Controller<M, S> {
    fun getInitialState(url: String): S
    suspend fun get(
        props: Params,
        params: URLSearchParams,
        state: S
    ): ControllerResult
}