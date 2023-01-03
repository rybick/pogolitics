package pogolitics

import pogolitics.api.Api
import pogolitics.controller.Controller
import pogolitics.controller.HomePageController
import pogolitics.controller.PokemonListController
import pogolitics.controller.PokemonListService
import pogolitics.controller.SinglePokemonController

class AppConfig {
    private val api = Api()
    private val pokemonListService = PokemonListService(api)

    private val mainPageController = HomePageController()
    private val pokemonListController = PokemonListController(pokemonListService)
    private val pokemonController = SinglePokemonController(api)

    // TODO later: move path to controller as well
    val routing: List<Route<out Any, out Any>> = listOf(
        Route("/", true, mainPageController),
        Route("/pokemon", true, pokemonListController),
        Route("/pokemon/:pokedexNumber", false, pokemonController)
    )

    class Route<M, S>(
        val path: String,
        val exact: Boolean,
        val controller: Controller<M, S>
    )

}