package pogolitics.controller

import js.core.get
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.w3c.dom.url.URLSearchParams
import pogolitics.ControllerResult
import pogolitics.api.*
import pogolitics.model.*
import pogolitics.model.SinglePokemonModel.PokemonIndividualStatistics
import pogolitics.model.SinglePokemonModel.VariablePokemonStatistics
import pogolitics.view.SinglePokemonNotFoundModel
import pogolitics.view.SinglePokemonNotFoundPage
import pogolitics.view.SinglePokemonPage
import react.router.Params
import kotlin.math.sqrt

class SinglePokemonController(
    private val api: Api,
    private val pokemonIndexService: PokemonIndexService
): Controller<PokemonIndividualValuesState> {

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
    ): ControllerResult {
        return coroutineScope {
            val pokedexNumber: Int = props.pokedexNumber
            val requestedForm: String? = params.form
            val mode = params.mode
            val pokemonIndex: Deferred<Array<PokemonIndexEntryDto>> = async { api.fetchPokemonIndex() }
            val fastMoves: Deferred<Array<FastMoveDto>> = async { api.fetchFastMoves() }
            val chargedMoves: Deferred<Array<ChargedMoveDto>> = async { api.fetchChargedMoves() }
            val maybePokemonAndEntry: Deferred<PokemonAndEntryDto?> = async {
                pokemonIndex.await()
                    .findPokemonIndexEntry(pokedexNumber, requestedForm)
                    ?.let { entry -> PokemonAndEntryDto(api.fetchPokemon(entry.uniqueId), entry) }
            }
            maybePokemonAndEntry.await()?.let { dto ->
                val pokemonStats = calculatePokemonStatistics(dto.pokemon, state)
                ControllerResult.modelAndView(
                    view = SinglePokemonPage::class,
                    model = SinglePokemonModel(
                        mode = mode,
                        pokemon = toPokemonStaticInfo(dto.pokemon, dto.entry),
                        stats = pokemonStats,
                        moveSets = calculateMoveSets(
                            mode = mode,
                            pokemon = dto.pokemon,
                            fastMoves = fastMoves.await(),
                            chargedMoves = chargedMoves.await(),
                            pokemonIvs = pokemonStats,
                            includeRocketAttacks = true // TODO later
                        ),
                        pokemonIndex = pokemonIndexService.getPokemonList(),
                        focusedElement = state.focus
                    )
                )
            } ?: ControllerResult.modelAndView(
                view = SinglePokemonNotFoundPage::class,
                model = SinglePokemonNotFoundModel(
                    mode = mode,
                    pokedexNumber = pokedexNumber,
                    pokemonIndex = pokemonIndexService.getPokemonList()
                )
            )
        }
    }

    private val Params.pokedexNumber: Int get() = get("pokedexNumber")!!.toInt()
    private val URLSearchParams.form: String? get() = get("form")
    // TODO display some kind of error page for invalid values
    private val URLSearchParams.mode: BattleMode get() = BattleMode.fromString(get("mode") ?: "pvp")

    private fun Array<PokemonIndexEntryDto>.findPokemonIndexEntry(
        pokedexNumber: Int,
        form: String?
    ): PokemonIndexEntryDto? {
        val allPokemonForms = filter { it.pokedexNumber == pokedexNumber }
        return findForm(allPokemonForms, form)
    }

    private fun findForm(allPokemonForms: List<PokemonIndexEntryDto>, form: String?): PokemonIndexEntryDto? =
        if (form == null) {
            allPokemonForms.firstOrNull { it.formIndex == 0 } // if form not requested take the 1st one
        } else {
            allPokemonForms.firstOrNull { it.form.equals(form, ignoreCase = true) }
        }

    private fun toPokemonStaticInfo(pokemon: PokemonDto, entry: PokemonIndexEntryDto): SinglePokemonModel.PokemonStaticInfo {
        return SinglePokemonModel.PokemonStaticInfo(
            uniqueId = pokemon.uniqueId,
            pokedexNumber = pokemon.pokedexNumber,
            form = PokemonForm.ofNullable(entry.form, entry.formCostume),
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
        includeRocketAttacks: Boolean
    ): List<MoveSet> =
        MoveSetsMapper.create(mode, pokemon, fastMoves, chargedMoves, includeRocketAttacks)
            .getData(pokemonIvs.currentStats.level, pokemonIvs.ivs.attack)

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

    private data class PokemonAndEntryDto(val pokemon: PokemonDto, val entry: PokemonIndexEntryDto)
}


