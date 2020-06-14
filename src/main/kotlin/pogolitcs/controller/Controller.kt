package pogolitcs.controller

import pogolitcs.ModelAndView
import pogolitcs.PageRProps
import react.RComponent
import react.RProps
import react.RState
import kotlin.reflect.KClass

interface Controller<P: RProps, M, S> {
    fun getInitialState(url: String): S
    suspend fun get(props: P, state: S): ModelAndView<M, KClass<out RComponent<out PageRProps<M, S>, out RState>>>
}