package pogolitics

import pogolitics.api.Api
import pogolitics.controller.Controller
import pogolitics.controller.HomePageController
import pogolitics.controller.PokemonListController
import pogolitics.controller.PokemonIndexService
import pogolitics.controller.SinglePokemonController

class AppConfig {
    private val api = Api()
    val pokemonIndexService = PokemonIndexService(api)

    private val homePageController = HomePageController(pokemonIndexService)
    private val pokemonListController = PokemonListController(pokemonIndexService)
    private val pokemonController = SinglePokemonController(api, pokemonIndexService)

    // TODO later: move path to controller as well
    val routing: List<Route<out Any, out Any>> = listOf(
        Route("/", true, homePageController),
        Route("/pokemon", true, pokemonListController),
        Route("/pokemon/:pokedexNumber", false, pokemonController)
    )

    class Route<M, S>(
        val path: String,
        val exact: Boolean,
        val controller: Controller<S>
    )

}