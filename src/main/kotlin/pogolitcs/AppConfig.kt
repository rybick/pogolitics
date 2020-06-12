package pogolitcs

import pogolitcs.api.Api
import pogolitcs.controller.MainPageController
import pogolitcs.controller.SinglePokemonController
import pogolitcs.model.PokemonIndividualValues
import pogolitcs.model.SinglePokemonModel
import react.RComponent
import react.RProps
import react.RState
import kotlin.reflect.KClass

class AppConfig {
    private val api = Api()

    private val mainPageController = MainPageController()
    private val pokemonController = SinglePokemonController(api)

    val routing: List<Route<out Any, out Any>> = listOf(
        Route<Unit, Unit>("/", true, Unit) { _, _ -> mainPageController.get() },
        Route<SinglePokemonModel, PokemonIndividualValues>("/pokemon/:id", false, PokemonIndividualValues(40.0F, 15, 15, 15)) { props, ivs -> pokemonController.get(props.id, ivs) }
    )

    class Route<M, S>(
        val path: String,
        val exact: Boolean,
        val initialPageState: S,
        val controllerMethod: suspend (IdRProps, S) -> ModelAndView<M, KClass<out RComponent<out PageRProps<M, S>, out RState>>>
    )

    interface IdRProps : RProps { // TODO a way to use different IN parameters per pogolitcs.controller
        var id: Int
    }
}