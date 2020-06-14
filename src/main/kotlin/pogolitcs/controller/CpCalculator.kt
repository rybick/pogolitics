package pogolitcs.controller

import kotlin.math.floor
import kotlin.math.sqrt

class CpCalculator(
    private val pokemon: PokemonData,
    private val individualPokemonStats: IndividualPokemonStats
) {
    data class PokemonData(
            // TODO think if merging it with MoveSetStatsCalculator PokemonData OR PokemonIndividualValues is a good idea
            // Probably the 2nd one but a little bit dirrerently...
        val baseAttack: Int,
        val baseDefense: Int,
        val baseStamina: Int
    )

    data class IndividualPokemonStats(val level: Float, val attack: Int, val defense: Int, val stamina: Int)

    fun calcCp(): Int {
        val attack = calcStatValue(pokemon.baseAttack, individualPokemonStats.attack, individualPokemonStats.level)
        val defense = calcStatValue(pokemon.baseDefense, individualPokemonStats.defense, individualPokemonStats.level)
        val stamina = calcStatValue(pokemon.baseStamina, individualPokemonStats.stamina, individualPokemonStats.level)
        return floor(0.1 * attack * sqrt(defense * stamina)).toInt()
    }

}