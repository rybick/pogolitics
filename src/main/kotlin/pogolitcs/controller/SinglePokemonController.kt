package pogolitcs.controller

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import pogolitcs.ModelAndView
import pogolitcs.api.Api
import pogolitcs.api.ChargedMoveDto
import pogolitcs.api.FastMoveDto
import pogolitcs.api.PokemonDto
import pogolitcs.model.IVs
import pogolitcs.model.MoveSet
import pogolitcs.model.PokemonIndividualValuesState
import pogolitcs.model.SinglePokemonModel
import pogolitcs.model.SinglePokemonModel.PokemonIndividualStatistics
import pogolitcs.model.SinglePokemonModel.VariablePokemonStatistics
import pogolitcs.view.SinglePokemonPage
import react.RProps
import kotlin.reflect.KClass

class SinglePokemonController(private val api: Api): Controller<SinglePokemonController.IdRProps, SinglePokemonModel, PokemonIndividualValuesState> {

    interface IdRProps : RProps {
        var id: String
    }

    override fun getInitialState(url: String) =
            PokemonIndividualValuesState(
                level = 40.0F,
                attack = 15,
                defense = 15,
                stamina = 15,
                cp = null
            )

    override suspend fun get(props: IdRProps, state: PokemonIndividualValuesState): ModelAndView<SinglePokemonModel, KClass<SinglePokemonPage>> {
        return coroutineScope {
            val pokemon: Deferred<PokemonDto> = async { api.fetchPokemon(props.id.toInt()) }
            val fastMoves: Deferred<Array<FastMoveDto>> = async { api.fetchFastMoves() }
            val chargedMoves: Deferred<Array<ChargedMoveDto>> = async { api.fetchChargedMoves() }
            val pokemonStats = calculatePokemonStatistics(pokemon.await(), state)
            ModelAndView(
                view = SinglePokemonPage::class,
                model = SinglePokemonModel(
                    pokemon = toPokemonStaticInfo(pokemon.await()),
                    stats = pokemonStats,
                    moveSets = calculateMoveSets(pokemon.await(), fastMoves.await(), chargedMoves.await(), pokemonStats)
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
        pokemonIvs: PokemonIndividualStatistics
    ): List<MoveSet> {
        return MoveSetsMapper(pokemon, fastMoves, chargedMoves).getData(pokemonIvs.currentStats.level, pokemonIvs.ivs.attack)
    }

    private fun calculatePokemonStatistics(pokemon: PokemonDto, pokemonIvs: PokemonIndividualValuesState): PokemonIndividualStatistics {
        val calculator = CpCalculator(
            CpCalculator.PokemonData(pokemon.baseAttack, pokemon.baseDefense, pokemon.baseStamina),
            CpCalculator.PokemonIv(pokemonIvs.attack, pokemonIvs.defense, pokemonIvs.stamina)
        )
        if (pokemonIvs.level != null) {
            return PokemonIndividualStatistics(
                ivs = IVs(
                    attack = pokemonIvs.attack,
                    defense = pokemonIvs.defense,
                    stamina = pokemonIvs.stamina
                ),
                currentStats = VariablePokemonStatistics(
                    cp = calculator.calcCp(pokemonIvs.level!!),
                    level = pokemonIvs.level!!
                ),
                bestGreatLeagueStats = toVariablePokemonStatistics(calculator.calcBestGreatLeagueCp()),
                bestUltraLeagueStats = toVariablePokemonStatistics(calculator.calcBestUltraLeagueCp())
            )
        } else {
            val calcLevelResult = calculator.calcLevel(pokemonIvs.cp!!)
            return PokemonIndividualStatistics(
                ivs = IVs(
                    attack = pokemonIvs.attack,
                    defense = pokemonIvs.defense,
                    stamina = pokemonIvs.stamina
                ),
                currentStats = VariablePokemonStatistics(
                    cp = calcLevelResult.cp,
                    level = calcLevelResult.level
                ),
                bestGreatLeagueStats = toVariablePokemonStatistics(calculator.calcBestGreatLeagueCp()),
                bestUltraLeagueStats = toVariablePokemonStatistics(calculator.calcBestUltraLeagueCp())
            )
        }
    }

    private fun toVariablePokemonStatistics(calcLevelRes: CpCalculator.CalcLevelResult): VariablePokemonStatistics {
        return VariablePokemonStatistics(calcLevelRes.cp, calcLevelRes.level)
    }
}