package pogolitics.controller

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.w3c.dom.url.URLSearchParams
import pogolitics.ControllerResult
import pogolitics.api.Api
import pogolitics.api.PokemonIndexEntryDto
import pogolitics.model.PokemonListModel
import pogolitics.view.PokemonListPage
import react.RProps
import react.router.Params
import kotlin.reflect.KClass

class PokemonListController(private val api: Api) : Controller<PokemonListModel, Unit> {

    override fun getInitialState(url: String) {}

    override suspend fun get(
        props: Params,
        params: URLSearchParams,
        state: Unit
    ): ControllerResult<PokemonListModel, KClass<PokemonListPage>> =
        coroutineScope {
            val pokemonIndex: Deferred<Array<PokemonIndexEntryDto>> = async { api.fetchPokemonIndex() }
            ControllerResult.modelAndView(
                view = PokemonListPage::class,
                model = PokemonListModel(mapToPokemonEntries(pokemonIndex.await()))
            )
        }

    private fun mapToPokemonEntries(pokemonDtos: Array<PokemonIndexEntryDto>): List<PokemonListModel.PokemonEntry> =
        pokemonDtos
            .groupBy { it.pokedexNumber }
            .mapValues { (pokedexNumber, entries) ->
                PokemonListModel.PokemonEntry(
                    pokedexNumber = pokedexNumber,
                    name = entries.first().name,
                    forms = entries
                        .map {
                            PokemonListModel.Form(
                                name = it.form,
                                prettyName = toPrettyName(it.form),
                                uniqueId = it.uniqueId
                            )
                        }
                )
            }
            .values
            .sortedBy { it.pokedexNumber }

    private fun toPrettyName(form: String?) = form
        ?.run {
            lowercase()
                .replaceFirstChar { it.uppercase() }
                .replace("_", " ")
        }
        ?: "Default"

}