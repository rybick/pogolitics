package pogolitcs

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import pogolitcs.view.MovesetsRProps
import pogolitcs.view.MovesetsRState
import react.*
import react.dom.p
import react.router.dom.RouteResultProps
import react.router.dom.hashRouter
import react.router.dom.route
import react.router.dom.switch
import kotlin.browser.window
import kotlin.reflect.KClass

external interface AppState : RState {
    var modelAndView: ModelAndView<*, *>?
    var url: String?
    var pageState: Any?
    var pageStateChanged: Boolean
}

class App: RComponent<RProps, AppState>() {
    val appConfig = AppConfig()

    override fun AppState.init(props: RProps) {
        pageStateChanged = false
    }

    override fun RBuilder.render() {
        hashRouter {
            switch {
                appConfig.routing.forEach {
                    routeToPage(it)
                }
            }
        }
    }


    private fun <M, S> RBuilder.routeToPage(route: AppConfig.Route<M, S>): ReactElement {
        return route<AppConfig.IdRProps>(route.path, exact = route.exact) { props ->
            console.log(props.match.params.id) // TODO later remove
            println(window.location.href) // TODO later remove
            if (state.modelAndView != null && state.url == window.location.href) {
                if (state.pageStateChanged) {
                    orderStateReload(route, props)
                }
                state.pageStateChanged = false
                renderPage<M, S>()
            } else {
                state.pageStateChanged = false
                if (state.pageState == null) { // TODO later
                    state.pageState = route.initialPageState
                }
                orderStateReload(route, props)
                renderLoadingPage()
            }
        }
    }

    private fun RBuilder.renderLoadingPage(): ReactElement {
        return p { +"loading..." }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <S> orderStateReload(route: AppConfig.Route<*, S>, props: RouteResultProps<AppConfig.IdRProps>) {
        MainScope().launch {
            val modelAndView = route.controllerMethod(props.match.params, state.pageState!! as S)
            setState {
                this.url = window.location.href
                this.modelAndView = modelAndView
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <M, S> RBuilder.renderPage(): ReactElement {
        return child(state.modelAndView!!.view as KClass<RComponent<PageRProps<M, S>, RState>>) {
            attrs.model = state.modelAndView!!.model as M
            attrs.state = state.pageState!! as S
            attrs.updateState = {
                setState {
                    pageState = it
                    pageStateChanged = true
                }
            }
        }
    }

}
