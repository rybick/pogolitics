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
import pogolitcs.model.PokemonIndividualValuesState
import pogolitcs.model.SinglePokemonModel
import pogolitcs.model.SinglePokemonModel.PokemonIndividualStatistics
import pogolitcs.view.SinglePokemonPage
import kotlin.reflect.KClass

class SinglePokemonController(private val api: Api) {

    suspend fun get(id: String, pokemonIvs: PokemonIndividualValuesState): ModelAndView<SinglePokemonModel, KClass<SinglePokemonPage>> {
        return coroutineScope {
            val pokemon: Deferred<PokemonDto> = async { api.fetchPokemon(id.toInt()) }
            val fastMoves: Deferred<Array<FastMoveDto>> = async { api.fetchFastMoves() }
            val chargedMoves: Deferred<Array<ChargedMoveDto>> = async { api.fetchChargedMoves() }
            ModelAndView(
                view = SinglePokemonPage::class,
                model = SinglePokemonModel(
                    pokemon = toPokemonStaticInfo(pokemon.await()),
                    stats = calculatePokemonStatistics(pokemon.await(), pokemonIvs),
                    moveSets = calculateMoveSets(pokemon.await(), fastMoves.await(), chargedMoves.await(), pokemonIvs)
                )
            )
        }
    }

    private fun toPokemonStaticInfo(pokemon: PokemonDto): SinglePokemonModel.PokemonStaticInfo {
        return SinglePokemonModel.PokemonStaticInfo(id = pokemon.id, name = pokemon.name)
    }

    private fun calculateMoveSets(
        pokemon: PokemonDto,
        fastMoves: Array<FastMoveDto>,
        chargedMoves: Array<ChargedMoveDto>,
        pokemonIvs: PokemonIndividualValuesState
    ): List<MoveSet> {
        return MoveSetsMapper(pokemon, fastMoves, chargedMoves).getData(pokemonIvs.level, pokemonIvs.attack)
    }

    private fun calculatePokemonStatistics(pokemon: PokemonDto, pokemonIvs: PokemonIndividualValuesState): PokemonIndividualStatistics {
        val calculator = CpCalculator(
            CpCalculator.PokemonData(pokemon.baseAttack, pokemon.baseDefense, pokemon.baseStamina),
            CpCalculator.IndividualPokemonStats(pokemonIvs.level, pokemonIvs.attack, pokemonIvs.defense, pokemonIvs.stamina)
        )
        return PokemonIndividualStatistics(
            cp = calculator.calcCp(),
            level = pokemonIvs.level,
            attack = pokemonIvs.attack,
            defense = pokemonIvs.defense,
            stamina = pokemonIvs.stamina
        )
    }
}