package pogolitics

import history.Location
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.w3c.dom.url.URLSearchParams
import pogolitics.view.NotFoundModel
import pogolitics.view.NotFoundPage
import react.*
import react.dom.p
import react.router.*
import react.router.dom.HashRouter
import kotlinx.browser.window
import kotlin.reflect.KClass

external interface AppState : RState {
    var controllerResult: ControllerResult<*, *>?
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
        HashRouter {
            Routes {
                appConfig.routing.forEach {
                    routeToPage(it)
                }
                Route {
                    attrs.path = "/"
                    attrs.element = createElement {
                        renderNotFoundPage("Invalid path")
                    }
                }
            }
        }
    }

    private fun <M, S> RBuilder.routeToPage(route: AppConfig.Route<M, S>) {
        Route {
            attrs.path = route.path
            attrs.element = createElement {
                child(fc { // workaround to be able to call useParams(), perhaps it can be done cleane
                    val params: Params = useParams()
                    val location: Location = useLocation()
                    val url = window.location.href
                    if (state.controllerResult != null && state.url == url) {
                        if (state.pageStateChanged) {
                            orderStateReload(route, params, location)
                        }
                        state.pageStateChanged = false
                        renderPage<M, S>()
                    } else {
                        state.pageStateChanged = false
                        state.pageState = route.controller.getInitialState(url)
                        orderStateReload(route, params, location)
                        renderLoadingPage()
                    }
                })
            }
        }
    }

    private fun RBuilder.renderLoadingPage(): ReactElement {
        return createElement { p { +"loading..." } }!!
    }

    private fun RBuilder.renderNotFoundPage(reason: String) {
        return child(NotFoundPage::class) {
            attrs.model = NotFoundModel(reason)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <S> orderStateReload(route: AppConfig.Route<*, S>, params: Params, location: Location) {
        MainScope().launch {
            val controllerResult =
                route.controller.get(
                    props = params,
                    params = URLSearchParams(location.search),
                    state = state.pageState!! as S
                )
            setState {
                this.url = window.location.href
                this.controllerResult = controllerResult
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <M, S> RBuilder.renderPage() {
        return if (state.controllerResult!!.isModelAndView) {
            child(state.controllerResult!!.view as KClass<RComponent<PageRProps<M, S>, RState>>) {
                attrs.model = state.controllerResult!!.model as M
                //attrs.state = state.pageState!! as S
                attrs.updateState = {
                    setState {
                        pageState = it
                        pageStateChanged = true
                    }
                }
            }
        } else {
            renderNotFoundPage(state.controllerResult!!.notFoundReason!!)
        }
    }

}
