package pogolitics

import pogolitics.api.Api
import pogolitics.controller.Controller
import pogolitics.controller.MainPageController
import pogolitics.controller.PokemonListController
import pogolitics.controller.SinglePokemonController
import react.RProps

class AppConfig {
    private val api = Api()

    private val mainPageController = MainPageController()
    private val pokemonListController = PokemonListController(api)
    private val pokemonController = SinglePokemonController(api)

    // TODO later: move path to controller as well
    val routing: List<Route<out RProps, out Any, out Any>> = listOf(
        Route("/", true, mainPageController),
        Route("/pokemon", true, pokemonListController),
        Route("/pokemon/:pokedexNumber", false, pokemonController)
    )

    class Route<P: RProps, M, S>(
        val path: String,
        val exact: Boolean,
        val controller: Controller<P, M, S>
    )

}