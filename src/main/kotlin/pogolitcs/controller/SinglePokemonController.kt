package pogolitcs.controller

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import pogolitcs.ModelAndView
import pogolitcs.api.Api
import pogolitcs.api.ChargedMoveDto
import pogolitcs.api.FastMoveDto
import pogolitcs.api.PokemonDto
import pogolitcs.model.MoveSet
import pogolitcs.model.SinglePokemonModel
import pogolitcs.view.SinglePokemonPage
import kotlin.reflect.KClass

class SinglePokemonController(val api: Api) {

    suspend fun get(id: Int): ModelAndView<SinglePokemonModel, KClass<SinglePokemonPage>> {
        return coroutineScope {
            val pokemon: Deferred<PokemonDto> = async { api.fetchPokemon(id) }
            val fastMoves: Deferred<Array<FastMoveDto>> = async { api.fetchFastMoves() }
            val chargedMoves: Deferred<Array<ChargedMoveDto>> = async { api.fetchChargedMoves() }
            ModelAndView(
                view = SinglePokemonPage::class,
                model = SinglePokemonModel(
                    pokemon = toPokemon(pokemon.await()),
                    moveSets = toMoveSets(pokemon.await(), fastMoves.await(), chargedMoves.await())
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
        chargedMoves: Array<ChargedMoveDto>
    ): List<MoveSet> {
        return MoveSetsMapper(pokemon, fastMoves, chargedMoves).getData()
    }
}