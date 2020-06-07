import api.Api
import react.RComponent
import react.RProps
import react.RState
import kotlin.reflect.KClass

class AppConfig {
    private val api = Api

    private val mainPageController = MainPageController()
    private val pokemonController = PokemonController(api)

    val routing: List<Route<out Any>> = listOf(
        Route("/", true) { mainPageController.get() },
        Route("/pokemon/:id", false) { pokemonController.get(it.id) }
    )

    class Route<M>(
        val path: String,
        val exact: Boolean,
        val controllerMethod: suspend (IdRProps) -> ModelAndView<M, KClass<out RComponent<out PageRProps<M>, out RState>>>
    )

    interface IdRProps : RProps { // TODO a way to use different IN parameters per controller
        var id: Int
    }
}