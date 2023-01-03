package pogolitics.controller

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.w3c.dom.url.URLSearchParams
import pogolitics.ControllerResult
import pogolitics.api.Api
import pogolitics.api.PokemonIndexEntryDto
import pogolitics.model.PokemonEntry
import pogolitics.model.PokemonForm
import pogolitics.model.PokemonListModel
import pogolitics.view.PokemonListPage
import react.router.Params
import kotlin.reflect.KClass

class PokemonListController(private val pokemonListService: PokemonListService) : Controller<PokemonListModel, Unit> {

    override fun getInitialState(url: String) {}

    override suspend fun get(
        props: Params,
        params: URLSearchParams,
        state: Unit
    ): ControllerResult<PokemonListModel, KClass<PokemonListPage>> =
        ControllerResult.modelAndView(
            view = PokemonListPage::class,
            model = PokemonListModel(pokemonListService.getPokemonList())
        )



}