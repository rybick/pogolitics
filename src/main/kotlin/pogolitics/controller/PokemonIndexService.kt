package pogolitics.controller

import pogolitics.api.Api
import pogolitics.api.PokemonIndexEntryDto
import pogolitics.model.PokemonEntry
import pogolitics.model.PokemonForm

class PokemonIndexService(private val api: Api) {

    suspend fun getPokemonList(): List<PokemonEntry> =
        mapToPokemonEntries(api.fetchPokemonIndex())

    private fun mapToPokemonEntries(pokemonDtos: Array<PokemonIndexEntryDto>): List<PokemonEntry> =
        pokemonDtos
            .groupBy { it.pokedexNumber }
            .mapValues { (pokedexNumber, entries) ->
                PokemonEntry(
                    pokedexNumber = pokedexNumber,
                    name = entries.first().name,
                    forms = entries
                        .filter { it.formIndex != null }
                        .sortedBy { it.formIndex }
                        .sortedBy { it.formCostume } // costumes last
                        .map { PokemonForm.ofNullable(it.form, it.formCostume) }
                )
            }
            .values
            .sortedBy { it.pokedexNumber }
}