package pogolitics.controller

import org.w3c.dom.url.URLSearchParams
import pogolitics.ControllerResult
import react.router.Params

interface Controller<S> {
    fun getInitialState(url: String): S
    suspend fun get(
        props: Params,
        params: URLSearchParams,
        state: S
    ): ControllerResult
}