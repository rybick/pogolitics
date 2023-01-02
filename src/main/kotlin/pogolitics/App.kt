package pogolitics

import csstype.Opacity
import csstype.TextAlign
import csstype.number
import csstype.pct
import emotion.react.css
import history.Location
import js.core.jso
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.w3c.dom.url.URLSearchParams
import pogolitics.view.NotFoundModel
import pogolitics.view.NotFoundPage
import react.*
import react.router.*
import react.router.dom.HashRouter
import kotlinx.browser.window
import pogolitics.view.StyleConstants
import pogolitics.view.component.Header
import pogolitics.view.loadingImagePath
import pogolitics.view.renderPage
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.p
import kotlin.reflect.KClass

external interface AppState : State {
    var controllerResult: ControllerResult<*, *>?
    var url: String?
    var pageState: Any?
    var pageStateChanged: Boolean
}

class App: Component<Props, AppState>() {
    val appConfig = AppConfig()

    init {
        state = jso {
            pageStateChanged = false
        }
    }

    override fun render() = Fragment.create {
        HashRouter {
            Routes {
                appConfig.routing.forEach {
                    routeToPage(it)
                }
                Route {
                    path = "/"
                    element = Fragment.create {
                        renderNotFoundPage("Invalid path")
                    }
                }
            }
        }
    }

    private fun <M, S> ChildrenBuilder.routeToPage(route: AppConfig.Route<M, S>) {
        Route {
            path = route.path
            element = wrapInFc {
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
                    +renderLoadingPage()
                }
            }
        }
    }

    private fun wrapInFc(block: ChildrenBuilder.(props: Props) -> Unit): ReactNode {
        return FC(block).create() // workaround to be able to call useParams(), perhaps it can be done cleaner
    }

    private fun renderLoadingPage() = renderPage(null) {
        div {
            css {
                padding = StyleConstants.Padding.big
                textAlign = TextAlign.center
                opacity = number(0.1)
            }
            img { src = loadingImagePath() }
        }
    }

    private fun ChildrenBuilder.renderNotFoundPage(reason: String) {
        NotFoundPage::class.react {
            model = NotFoundModel(reason)
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
            setState({ state ->
                state.url = window.location.href
                state.controllerResult = controllerResult
                state
            })
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <M, S> ChildrenBuilder.renderPage() {
        return if (state.controllerResult!!.isModelAndView) {
            val component = state.controllerResult!!.view as KClass<Component<PageRProps<M, S>, State>>
            component.react {
                model = state.controllerResult!!.model as M
                //attrs.state = state.pageState!! as S
                updateState = {
                    setState({state ->
                        state.pageState = it
                        state.pageStateChanged = true
                        state
                    })
                }
            }
        } else {
            renderNotFoundPage(state.controllerResult!!.notFoundReason!!)
        }
    }

}
