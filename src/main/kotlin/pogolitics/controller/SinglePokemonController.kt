package pogolitics.controller

import js.core.get
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.w3c.dom.url.URLSearchParams
import pogolitics.TypedControllerResult
import pogolitics.api.*
import pogolitics.model.*
import pogolitics.model.SinglePokemonModel.PokemonIndividualStatistics
import pogolitics.model.SinglePokemonModel.VariablePokemonStatistics
import pogolitics.view.SinglePokemonPage
import react.router.Params
import kotlin.math.sqrt

class SinglePokemonController(
    private val api: Api,
    private val pokemonIndexService: PokemonIndexService
): Controller<Any, PokemonIndividualValuesState> {

    override fun getInitialState(url: String) =
        PokemonIndividualValuesState(
            level = 40.0F,
            attack = 15,
            defense = 15,
            stamina = 15,
            cp = null
        )

    override suspend fun get(
        props: Params,
        params: URLSearchParams,
        state: PokemonIndividualValuesState
    ): TypedControllerResult<*, *> {
        return coroutineScope {
            val form = params.get("form")
            val mode = BattleMode.fromString(params.get("mode") ?: "pvp") // TODO display some kind of error page for invalid values
            val pokemonIndex: Deferred<Array<PokemonIndexEntryDto>> = async { api.fetchPokemonIndex() }
            val fastMoves: Deferred<Array<FastMoveDto>> = async { api.fetchFastMoves() }
            val chargedMoves: Deferred<Array<ChargedMoveDto>> = async { api.fetchChargedMoves() }
            val maybePokemon: Deferred<PokemonDto?> = async {
                pokemonIndex.await()
                    .findPokemonUniqueId(props["pokedexNumber"]!!, form)
                    ?.let { uniqueId -> api.fetchPokemon(uniqueId) }
            }
            maybePokemon.await()?.let { pokemon ->
                val pokemonStats = calculatePokemonStatistics(pokemon, state)
                TypedControllerResult.modelAndView(
                    view = SinglePokemonPage::class,
                    model = SinglePokemonModel(
                        mode = mode,
                        pokemon = toPokemonStaticInfo(pokemon, form),
                        stats = pokemonStats,
                        moveSets = calculateMoveSets(
                            mode = mode,
                            pokemon = pokemon,
                            fastMoves = fastMoves.await(),
                            chargedMoves = chargedMoves.await(),
                            pokemonIvs = pokemonStats
                        ),
                        pokemonIndex = pokemonIndexService.getPokemonList(),
                        focusedElement = state.focus
                    )
                )
            } ?: TypedControllerResult.notFound("No such pokemon")
        }
    }

    private fun Array<PokemonIndexEntryDto>.findPokemonUniqueId(pokedexNumber: String, form: String?): String? =
        pokedexNumber.toInt().let { pokedexNumberInt ->
            filter { it.pokedexNumber == pokedexNumberInt }
                .firstOrNull { it.form.equals(form, ignoreCase = true) }
                ?.uniqueId
        }

    private fun toPokemonStaticInfo(pokemon: PokemonDto, form: String?): SinglePokemonModel.PokemonStaticInfo {
        return SinglePokemonModel.PokemonStaticInfo(
            uniqueId = pokemon.uniqueId,
            pokedexNumber = pokemon.pokedexNumber,
            form = PokemonForm.ofNullable(form),
            name = pokemon.name,
            types = pokemon.types.toModel(),
            baseAttack = pokemon.baseAttack,
            baseDefense = pokemon.baseDefense,
            baseStamina = pokemon.baseStamina,
            hardiness = calculateHardiness(pokemon.baseDefense, pokemon.baseStamina)
        )
    }

    private fun calculateHardiness(baseDefense: Int, baseStamina: Int): Double =
        sqrt((baseDefense * baseStamina).toDouble())

    private fun calculateHardiness(defense: Double, stamina: Double): Double =
        sqrt(defense * stamina)

    private fun calculateMoveSets(
        mode: BattleMode,
        pokemon: PokemonDto,
        fastMoves: Array<FastMoveDto>,
        chargedMoves: Array<ChargedMoveDto>,
        pokemonIvs: PokemonIndividualStatistics,
    ): List<MoveSet> {
        return MoveSetsMapper.create(mode, pokemon, fastMoves, chargedMoves)
            .getData(pokemonIvs.currentStats.level, pokemonIvs.ivs.attack)
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
                currentStats = toVariablePokemonStatistics(calculator.calcStatisticsByLevel(pokemonIvs.level!!)),
                bestGreatLeagueStats = toVariablePokemonStatistics(calculator.calcStatisticsByCp(MAX_GREAT_CP)),
                bestUltraLeagueStats = toVariablePokemonStatistics(calculator.calcStatisticsByCp(MAX_ULTRA_CP)),
                bestStatsWithoutBoost = variablePokemonStatisticsAtBasicMaxLevel(calculator)
            )
        } else {
            return PokemonIndividualStatistics(
                ivs = IVs(
                    attack = pokemonIvs.attack,
                    defense = pokemonIvs.defense,
                    stamina = pokemonIvs.stamina
                ),
                currentStats = toVariablePokemonStatistics(calculator.calcStatisticsByCp(pokemonIvs.cp!!)),
                bestGreatLeagueStats = toVariablePokemonStatistics(calculator.calcStatisticsByCp(MAX_GREAT_CP)),
                bestUltraLeagueStats = toVariablePokemonStatistics(calculator.calcStatisticsByCp(MAX_ULTRA_CP)),
                bestStatsWithoutBoost = variablePokemonStatisticsAtBasicMaxLevel(calculator)
            )
        }
    }

    private fun variablePokemonStatisticsAtBasicMaxLevel(calculator: CpCalculator): VariablePokemonStatistics {
        return convertToVariablePokemonStatistics(calculator.calcStatisticsByLevel(BASIC_MAX_LEVEL))
    }

    private fun toVariablePokemonStatistics(calculatedStatistics: CpCalculator.CalculatedPokemonStatistics): VariablePokemonStatistics {
        return convertToVariablePokemonStatistics(calculatedStatistics)
    }

    private fun convertToVariablePokemonStatistics(calculatedStatistics: CpCalculator.CalculatedPokemonStatistics): VariablePokemonStatistics {
        return VariablePokemonStatistics(
            cp = calculatedStatistics.cp,
            level = calculatedStatistics.level,
            attack = calculatedStatistics.attack,
            defense = calculatedStatistics.defense,
            stamina = calculatedStatistics.stamina,
            hardiness = calculateHardiness(calculatedStatistics.defense, calculatedStatistics.stamina)
        )
    }

    private fun PokemonDto.TypesDto.toModel(): SinglePokemonModel.PokemonStaticInfo.Types =
        SinglePokemonModel.PokemonStaticInfo.Types(
            primary = PokemonType.fromString(primary),
            secondary = secondary?.let { PokemonType.fromString(it) }
        )
}


