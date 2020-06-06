import api.Api
import react.RComponent
import react.RProps
import react.RState
import kotlin.reflect.KClass

class AppConfig {
    val api = Api

    val mainPageController = MainPageController()
    val pokemonController = PokemonController(api)

//    val routing = mapOf<String, Any>(
//        "/" to { mainPageController.get() }
//        //"/pokemon/:id" to { id -> pokemonController.get(id) }
//    )

    val routing: List<Route<out Any>> = listOf(
        Route("/", true) { mainPageController.get() },
        Route("/pokemon/:id", false) { pokemonController.get(it.id) }
    )

    class Route<M>(
        val path: String,
        val exact: Boolean,
        val controllerMethod: suspend (IdRProps) -> ModelAndView<M, KClass<out RComponent<out PageRProps<M>, out RState>>>
    )

    interface IdRProps : RProps { // TODO
        var id: Int
    }
}