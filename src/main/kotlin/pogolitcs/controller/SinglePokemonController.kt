package pogolitcs.controller

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import pogolitcs.ModelAndView
import pogolitcs.api.Api
import pogolitcs.api.ChargedMoveDto
import pogolitcs.api.FastMoveDto
import pogolitcs.api.PokemonDto
import pogolitcs.model.MoveSet
import pogolitcs.model.PokemonIndividualValues
import pogolitcs.model.SinglePokemonModel
import pogolitcs.view.SinglePokemonPage
import kotlin.reflect.KClass

class SinglePokemonController(val api: Api) {

    suspend fun get(id: Int, pokemonIvs: PokemonIndividualValues): ModelAndView<SinglePokemonModel, KClass<SinglePokemonPage>> {
        return coroutineScope {
            val pokemon: Deferred<PokemonDto> = async { api.fetchPokemon(id) }
            val fastMoves: Deferred<Array<FastMoveDto>> = async { api.fetchFastMoves() }
            val chargedMoves: Deferred<Array<ChargedMoveDto>> = async { api.fetchChargedMoves() }
            ModelAndView(
                view = SinglePokemonPage::class,
                model = SinglePokemonModel(
                    pokemon = toPokemon(pokemon.await()),
                    moveSets = toMoveSets(pokemon.await(), fastMoves.await(), chargedMoves.await(), pokemonIvs),
                    pokemonIndividualValues = pokemonIvs
                )
            )
        }
    }

    private fun toPokemon(pokemon: PokemonDto): SinglePokemonModel.Pokemon {
        return SinglePokemonModel.Pokemon(id = pokemon.id, name = pokemon.name)
    }

    private fun toMoveSets(
            pokemon: PokemonDto,
            fastMoves: Array<FastMoveDto>,
            chargedMoves: Array<ChargedMoveDto>,
            pokemonIvs: PokemonIndividualValues
    ): List<MoveSet> {
        return MoveSetsMapper(pokemon, fastMoves, chargedMoves).getData(pokemonIvs.level, pokemonIvs.attack)
    }
}