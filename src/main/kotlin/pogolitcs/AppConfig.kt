package pogolitcs

import pogolitcs.api.Api
import pogolitcs.controller.Controller
import pogolitcs.controller.MainPageController
import pogolitcs.controller.SinglePokemonController
import pogolitcs.model.PokemonIndividualValuesState
import pogolitcs.model.SinglePokemonModel
import react.RComponent
import react.RProps
import react.RState
import kotlin.reflect.KClass

class AppConfig {
    private val api = Api()

    private val mainPageController = MainPageController()
    private val pokemonController = SinglePokemonController(api)

    // TODO later: move path to controller as well
    val routing: List<Route<out RProps, out Any, out Any>> = listOf(
        Route("/", true, mainPageController),
        Route("/pokemon/:pokedexNumber", false, pokemonController)
    )

    class Route<P: RProps, M, S>(
        val path: String,
        val exact: Boolean,
        val controller: Controller<P, M, S>
    )

}