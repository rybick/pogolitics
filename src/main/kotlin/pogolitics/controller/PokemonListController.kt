package pogolitics.controller

import org.w3c.dom.url.URLSearchParams
import pogolitics.ControllerResult
import pogolitics.model.PokemonListModel
import pogolitics.view.PokemonListPage
import react.router.Params

class PokemonListController(private val pokemonIndexService: PokemonIndexService) : Controller<PokemonListModel, Unit> {

    override fun getInitialState(url: String) {}

    override suspend fun get(
        props: Params,
        params: URLSearchParams,
        state: Unit
    ): ControllerResult =
        ControllerResult.modelAndView(
            view = PokemonListPage::class,
            model = PokemonListModel(pokemonIndexService.getPokemonList())
        )

}