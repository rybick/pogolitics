package pogolitcs

import pogolitcs.api.Api
import pogolitcs.controller.MainPageController
import pogolitcs.controller.SinglePokemonController
import pogolitcs.model.PokemonIndividualValues
import react.RComponent
import react.RProps
import react.RState
import kotlin.reflect.KClass

class AppConfig {
    private val api = Api()

    private val mainPageController = MainPageController()
    private val pokemonController = SinglePokemonController(api)

    val routing: List<Route<out Any>> = listOf(
        Route("/", true) { x, y -> mainPageController.get() },
        Route("/pokemon/:id", false) { props, ivs -> pokemonController.get(props.id, ivs) }
    )

    class Route<M>(
        val path: String,
        val exact: Boolean,
        val controllerMethod: suspend (IdRProps, PokemonIndividualValues) -> ModelAndView<M, KClass<out RComponent<out PageRProps<M>, out RState>>>
    )

    interface IdRProps : RProps { // TODO a way to use different IN parameters per pogolitcs.controller
        var id: Int
    }
}