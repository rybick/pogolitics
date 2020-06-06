import api.ChargedMoveDto
import api.FastMoveDto
import api.PokemonDto
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.*
import react.dom.p
import react.router.dom.hashRouter
import react.router.dom.route
import react.router.dom.switch
import kotlin.browser.window
import kotlin.reflect.KClass

external interface AppState1 : RState {
    var pokemonData: PokemonDto?
    var fastMoves: Array<FastMoveDto>?
    var chargedMoves: Array<ChargedMoveDto>?
}

fun AppState1.isReady() = this.pokemonData != null && this.fastMoves != null && this.chargedMoves != null

external interface AppState : RState {
    //var data: ModelAndView<PokemonController.Model, KClass<PokemonPage>>? // TODO
    //var data: ModelAndView<Any, KClass<out RComponent<out PageRProps<Any>, out RState>>>?
    var data: ModelAndView<*, *>?
    var url: String?
}

class App: RComponent<RProps, AppState>() {
    val appConfig = AppConfig()

    override fun RBuilder.render() {
        hashRouter {
            switch {
                appConfig.routing.forEach {
                    routeToPage(it)
                }
//                route("/", exact = true) {
//                    child(MainPageController().get().view) {}
//                }
//                route<AppConfig.IdRProps>("/pokemon/:id") { props ->
//                    console.log(props.match.params.id)
//                    println(window.location.href)
//                    //rr(AppConfig().tmp)
//                        //view.moveSetsTable {
//                        //    values = if (state.isReady()) getMoveSets(state) else listOf()
//                        //}
//                        // TODO (1) dac linki do poprzedni/nastepny i zoabczyc jak to bedzie dzialac
//                    rr(AppConfig().tmp)
//                    if (state.data != null && state.url == window.location.href) {
//                        child(state.data!!.view as KClass<PokemonPage>) {
//                            attrs.model = state.data!!.model as PokemonController.Model
//                        }
//                    } else {
//                        MainScope().launch {
//                            val modelAndView = PokemonController(Api).get(props.match.params.id)
//                            setState {
//                                url = window.location.href
//                                data = modelAndView
//                            }
//                        }
//                        p { +"loading..." }
//                    }
//                }
            }
        }
    }

    private fun <M> RBuilder.routeToPage(route: AppConfig.Route<out M>): ReactElement {
        return route<AppConfig.IdRProps>(route.path, exact = route.exact) { props ->
            console.log(props.match.params.id) // TODO later remove
            println(window.location.href) // TODO later remove
            if (state.data != null && state.url == window.location.href) {
                child(state.data!!.view as KClass<RComponent<PageRProps<M>, RState>>) {
                    attrs.model = state.data!!.model as M
                }
            } else {
                MainScope().launch {
                    val modelAndView = route.controllerMethod(props.match.params)
                    setState {
                        url = window.location.href
                        data = modelAndView
                    }
                }
                p { +"loading..." }
            }
        }
    }
/*
    override fun AppState.init() {
        MainScope().launch {
            println(window.location.href)
            val _pokemon = Api.fetchPokemon(150)
            val _fastMoves = Api.fetchFastMoves()
            val _chargedMoves = Api.fetchChargedMoves()
            setState {
                pokemonData = _pokemon
                fastMoves = _fastMoves
                chargedMoves = _chargedMoves
            }
        }
    }

    private fun getMoveSets(state: AppState): List<view.MoveSet> {
        return MoveSetsTableDataMapper(state.pokemonData!!, state.fastMoves!!, state.chargedMoves!!)
            .getData()
    }*/
}
