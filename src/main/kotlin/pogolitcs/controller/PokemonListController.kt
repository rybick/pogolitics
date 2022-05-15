package pogolitcs.controller

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.w3c.dom.url.URLSearchParams
import pogolitcs.ControllerResult
import pogolitcs.api.Api
import pogolitcs.api.PokemonIndexEntryDto
import pogolitcs.model.PokemonListModel
import pogolitcs.view.PokemonListPage
import react.RProps
import kotlin.reflect.KClass

class PokemonListController(private val api: Api): Controller<RProps, PokemonListModel, Unit> {

    override fun getInitialState(url: String) = Unit

    override suspend fun get(
        props: RProps,
        params: URLSearchParams,
        state: Unit
    ): ControllerResult<PokemonListModel, KClass<PokemonListPage>> =
        coroutineScope {
            val pokemonIndex: Deferred<Array<PokemonIndexEntryDto>> = async { api.fetchPokemonIndex() }
            ControllerResult.modelAndView(
                view = PokemonListPage::class,
                model = PokemonListModel(pokemonIndex.await().map { toPokemonEntry(it) })
            )
        }

    private fun toPokemonEntry(pokemonDto: PokemonIndexEntryDto): PokemonListModel.PokemonEntry =
        with(pokemonDto) {
            PokemonListModel.PokemonEntry(
                uniqueId = uniqueId,
                pokedexNumber = pokedexNumber,
                name = name,
                form = form
            )
        }
}