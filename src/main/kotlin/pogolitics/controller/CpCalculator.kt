package pogolitics.controller

import kotlin.math.floor
import kotlin.math.sqrt

class CpCalculator(
    private val pokemon: PokemonData,
    private val pokemonIv: PokemonIv
) {
    data class PokemonData(
            // TODO think if merging it with MoveSetStatsCalculator PokemonData OR PokemonIndividualValues is a good idea
            // Probably the 2nd one but a little bit dirrerently...
        val baseAttack: Int,
        val baseDefense: Int,
        val baseStamina: Int
    )

    data class PokemonIv(val attack: Int, val defense: Int, val stamina: Int)

    data class CalculatedPokemonStatistics(
        val level: Float,
        val cp: Int,
        val matchingInputArgument: Boolean,
        val attack : Double,
        val defense : Double,
        val stamina : Double
    )

    fun calcStatisticsByLevel(level: Float): CalculatedPokemonStatistics {
        val attack = calcStatValue(pokemon.baseAttack, pokemonIv.attack, level)
        val defense = calcStatValue(pokemon.baseDefense, pokemonIv.defense, level)
        val stamina = calcStatValue(pokemon.baseStamina, pokemonIv.stamina, level)
        val cp = floor(0.1 * attack * sqrt(defense * stamina)).toInt()
        return CalculatedPokemonStatistics(
            level = level,
            cp = cp,
            attack = attack,
            stamina = stamina,
            defense = defense,
            matchingInputArgument = true
        )
    }

    fun calcStatisticsByCp(cp: Int): CalculatedPokemonStatistics {
        var level = 0.5F // smallest possible value (=1.0) - step (=0.5F)
        do {
            level += 0.5F
        } while (level <= ABSOLUTE_MAX_LEVEL && calcStatisticsByLevel(level).cp <= cp)
        if (level > 1.0F) {
            level -= 0.5F
        }
        val calculatedStatistics = calcStatisticsByLevel(level)
        return calculatedStatistics.copy(matchingInputArgument = calculatedStatistics.cp == cp)
    }

}