package pogolitcs

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import pogolitcs.model.PokemonIndividualValues
import react.*
import react.dom.p
import react.router.dom.hashRouter
import react.router.dom.route
import react.router.dom.switch
import kotlin.browser.window
import kotlin.reflect.KClass

external interface AppState : RState {
    var data: ModelAndView<*, *>?
    var url: String?
    var ivs: PokemonIndividualValues? // TODO later
}

class App: RComponent<RProps, AppState>() {
    val appConfig = AppConfig()

    override fun RBuilder.render() {
        hashRouter {
            switch {
                appConfig.routing.forEach {
                    routeToPage(it)
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <M> RBuilder.routeToPage(route: AppConfig.Route<out M>): ReactElement {
        return route<AppConfig.IdRProps>(route.path, exact = route.exact) { props ->
            console.log(props.match.params.id) // TODO later remove
            println(window.location.href) // TODO later remove
            if (state.data != null && state.url == window.location.href) {
                child(state.data!!.view as KClass<RComponent<PageRProps<M>, RState>>) {
                    attrs.model = state.data!!.model as M
                }
            } else {
                if (state.ivs == null) { // TODO later
                    state.ivs = PokemonIndividualValues(40.0F, 15, 15, 15)
                }
                MainScope().launch {
                    val modelAndView = route.controllerMethod(props.match.params, state.ivs!!)
                    setState {
                        url = window.location.href
                        data = modelAndView
                    }
                }
                p { +"loading..." }
            }
        }
    }

}
