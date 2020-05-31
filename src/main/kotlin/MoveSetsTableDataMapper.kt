import MoveSetStatsCalculator.*
import api.ChargedMoveDto
import api.FastMoveDto
import api.PokemonDto
import kotlin.time.seconds

class MoveSetsTableDataMapper(
    private val pokemonDto: PokemonDto,
    fastMoves: Array<FastMoveDto>,
    chargedMoves: Array<ChargedMoveDto>
) {
    private val quickAttacks: Map<String, FastMoveDto> = fastMoves.map { it.name to it }.toMap()
    private val chargedAttacks: Map<String, ChargedMoveDto> = chargedMoves.map { it.name to it }.toMap()

    fun getData(): List<MoveSet> {
        val pokemon = mapPokemonData(pokemonDto)
        return combinations(pokemonDto.moves.quick, pokemonDto.moves.charged) { fast, charged ->
            val fastMove = quickAttacks[fast.name] ?: throw MissingDataException("Unknown attack: $fast.name")
            val chargedMove = chargedAttacks[charged.name] ?: throw MissingDataException("Unknown attack: $charged.name")
            val calculator = MoveSetStatsCalculator(pokemon, mapFastMove(fastMove), mapChargedMove(chargedMove))
            MoveSet(
                quickAttack = Attack(PokemonType.fromString(fastMove.type), fastMove.name),
                chargedAttack = Attack(PokemonType.fromString(chargedMove.type), chargedMove.name),
                dps = calculator.dps().toFloat(),
                timeToFirstAttack = calculator.timeToFirstAttack()
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

    private fun mapFastMove(moveDto: FastMoveDto): MoveData {
        return MoveData(
            power = moveDto.pvp.power,
            energy = moveDto.pvp.energy,
            duration = moveDto.pvp.duration.pvpTurns,
            type = PokemonType.fromString(moveDto.type)
        )
    }

    private fun mapChargedMove(moveDto: ChargedMoveDto): MoveData {
        return MoveData(
            power = moveDto.pvp.power,
            energy = moveDto.pvp.energy,
            duration = 1.pvpTurns,
            type = PokemonType.fromString(moveDto.type)
        )
    }

    private fun <T, U, O> combinations(arr1: Array<T>, arr2: Array<U>, mapper: (T, U) -> O): List<O> {
        return arr1.flatMap { a1 -> arr2.map { a2 -> mapper(a1, a2) } }
    }
}
