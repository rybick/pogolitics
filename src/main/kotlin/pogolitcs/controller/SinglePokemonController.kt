package pogolitcs.controller

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
        val pokemon: PokemonDto = api.fetchPokemon(id)
        val fastMoves: Array<FastMoveDto> = api.fetchFastMoves()
        val chargedMoves: Array<ChargedMoveDto> = api.fetchChargedMoves() // TODO later parallelize
        return ModelAndView(
                view = SinglePokemonPage::class,
                model = SinglePokemonModel(
                        pokemon = toPokemon(pokemon),
                        moveSets = toMoveSets(pokemon, fastMoves, chargedMoves)
                )
        )
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