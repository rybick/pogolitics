package pogolitcs.controller

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

    data class CalcLevelResult(val level: Float, val cp: Int, val exact: Boolean)

    private val MAX_LEVEL = 41.0F

    fun calcCp(level: Float): Int {
        val attack = calcStatValue(pokemon.baseAttack, pokemonIv.attack, level)
        val defense = calcStatValue(pokemon.baseDefense, pokemonIv.defense, level)
        val stamina = calcStatValue(pokemon.baseStamina, pokemonIv.stamina, level)
        return floor(0.1 * attack * sqrt(defense * stamina)).toInt()
    }

    fun calcLevel(cp: Int): CalcLevelResult {
        var level = 0.5F // smallest possible value (=1.0) - step (=0.5F)
        do {
            level += 0.5F
        } while (level <= MAX_LEVEL && calcCp(level) <= cp)
        if (level > 1.0F) {
            level -= 0.5F
        }
        val resultCp = calcCp(level)
        return CalcLevelResult(level = level, cp = resultCp, exact = resultCp == cp)
    }

}