package pogolitics.controller

import pogolitics.controller.MoveSetStatsCalculator.*
import pogolitics.api.ChargedMoveDto
import pogolitics.api.FastMoveDto
import pogolitics.api.PokemonDto
import pogolitics.model.Attack
import pogolitics.model.BattleMode
import pogolitics.model.MoveSet
import pogolitics.model.PokemonType
import pogolitics.pvpTurns
import kotlin.time.Duration.Companion.milliseconds

private class PvPMoveSetsMapper(
    pokemonDto: PokemonDto,
    fastMoves: Array<FastMoveDto>,
    chargedMoves: Array<ChargedMoveDto>
) : MoveSetsMapper(pokemonDto, fastMoves, chargedMoves) {
    override fun mapFastMove(moveDto: FastMoveDto): MoveData {
        return MoveData(
            power = moveDto.pvp.power,
            energy = moveDto.pvp.energy,
            duration = moveDto.pvp.duration.pvpTurns,
            type = PokemonType.fromString(moveDto.type)
        )
    }

    override fun mapChargedMove(moveDto: ChargedMoveDto): MoveData {
        return MoveData(
            power = moveDto.pvp.power,
            energy = moveDto.pvp.energy,
            duration = 1.pvpTurns,
            type = PokemonType.fromString(moveDto.type)
        )
    }
}

private class PvEMoveSetsMapper(
    pokemonDto: PokemonDto,
    fastMoves: Array<FastMoveDto>,
    chargedMoves: Array<ChargedMoveDto>
) : MoveSetsMapper(pokemonDto, fastMoves, chargedMoves) {
    override fun mapFastMove(moveDto: FastMoveDto): MoveData {
        return MoveData(
            power = moveDto.pve.power,
            energy = moveDto.pve.energy,
            duration = moveDto.pve.duration.milliseconds,
            type = PokemonType.fromString(moveDto.type)
        )
    }

    override fun mapChargedMove(moveDto: ChargedMoveDto): MoveData {
        return MoveData(
            power = moveDto.pve.power,
            energy = moveDto.pve.energy,
            duration = moveDto.pve.duration.milliseconds,
            type = PokemonType.fromString(moveDto.type)
        )
    }
}

sealed class MoveSetsMapper constructor(
    private val pokemonDto: PokemonDto,
    fastMoves: Array<FastMoveDto>,
    chargedMoves: Array<ChargedMoveDto>
) {
    companion object {
        fun create(
            mode: BattleMode,
            pokemonDto: PokemonDto,
            fastMoves: Array<FastMoveDto>,
            chargedMoves: Array<ChargedMoveDto>
        ): MoveSetsMapper = if (mode == BattleMode.PVP) {
            PvPMoveSetsMapper(pokemonDto, fastMoves, chargedMoves)
        } else {
            PvEMoveSetsMapper(pokemonDto, fastMoves, chargedMoves)
        }
    }

    private val quickAttacks: Map<String, FastMoveDto> = fastMoves.associateBy { it.id }
    private val chargedAttacks: Map<String, ChargedMoveDto> = chargedMoves.associateBy { it.id }

    fun getData(pokemonLevel: Float, pokemonAttackIv: Int): List<MoveSet> {
        val pokemon = mapPokemonData(pokemonDto)
        return combinations(pokemonDto.moves.quick, pokemonDto.moves.charged) { fast, charged ->
            val fastMove = quickAttacks[fast.id] ?: throw MissingDataException("Unknown attack: ${fast.id}")
            val chargedMove = chargedAttacks[charged.id] ?: throw MissingDataException("Unknown attack: ${charged.id}")
            val calculator = MoveSetStatsCalculator(
                pokemon = pokemon,
                fast = mapFastMove(fastMove),
                charged = mapChargedMove(chargedMove),
                individualPokemonStats = IndividualPokemonStats(pokemonLevel, pokemonAttackIv)
            )
            MoveSet(
                quickAttack = Attack(PokemonType.fromString(fastMove.type), fastMove.name, fast.elite),
                chargedAttack = Attack(PokemonType.fromString(chargedMove.type), chargedMove.name, charged.elite),
                dps = calculator.dps().toFloat(),
                timeToFirstAttack = calculator.timeToFirstAttack(),
                meanTimeBetweenAttacks = calculator.meanTimeBetweenAttacks()
            )
        }
    }

    private fun mapPokemonData(pokemonDto: PokemonDto): PokemonData {
        return PokemonData(
            baseAttack = pokemonDto.baseAttack,
            baseDefense = pokemonDto.baseDefense,
            baseStamina = pokemonDto.baseStamina,
            types = pokemonDto.types.let {
                PokemonTypes(PokemonType.fromString(it.primary), it.secondary?.let { PokemonType.fromString(it) })
            }
        )
    }

    protected abstract fun mapFastMove(moveDto: FastMoveDto): MoveData

    protected abstract fun mapChargedMove(moveDto: ChargedMoveDto): MoveData

    private fun <T, U, O> combinations(arr1: Array<T>, arr2: Array<U>, mapper: (T, U) -> O): List<O> {
        return arr1.flatMap { a1 -> arr2.map { a2 -> mapper(a1, a2) } }
    }
}
