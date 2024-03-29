package pogolitics.controller

import pogolitics.model.PokemonType
import kotlin.math.ceil
import kotlin.time.Duration
import kotlin.time.DurationUnit.SECONDS

class MoveSetStatsCalculator(
        private val pokemon: PokemonData,
        private val fast: MoveData,
        private val charged: MoveData,
        private val individualPokemonStats: IndividualPokemonStats
) {
    data class PokemonData(
        val baseAttack: Int,
        val baseDefense: Int,
        val baseStamina: Int,
        private val types: PokemonTypes
    ) {
        fun isOfType(type: PokemonType): Boolean {
            return types.primary == type || types.secondary == type
        }
    }

    data class PokemonTypes(val primary: PokemonType, val secondary: PokemonType? = null)

    data class MoveData(
        val power: Int,
        val energy: Int,
        val duration: Duration,
        val type: PokemonType
    )

    data class IndividualPokemonStats(val level: Float, val attack: Int)

    // cache fields
    private var dps: Double? = null
    private var effectiveFastAttackDps: Double? = null

    fun dps(): Double {
        if (dps == null) {
            dps = effectiveFastAttackDps() + chargedAttackDps()
        }
        return dps ?: throw RuntimeException("Should not happen")
    }

    fun timeToFirstAttack(): Duration = fast.duration * ceil(charged.energy.toDouble() / fast.energy)

    fun meanTimeBetweenAttacks(): Duration = fast.duration * charged.energy / fast.energy

    // DPS from fast attack ONLY, if charged attack is used (and therefore takes time to cast)
    fun effectiveFastAttackDps(): Double {
        if (effectiveFastAttackDps == null) {
            effectiveFastAttackDps = fastAttackDps() * (1 - chargedAttackDurationPerSecond())
        }
        return effectiveFastAttackDps ?: throw RuntimeException("Should not happen")
    }

    fun fastAttackDps(): Double = damage(fast) / fast.duration.toDouble(SECONDS)

    // all methods below could be private, but it's useful to be able to look into them (see MoveSetStatsCalculatorTest)
    // DPS if only fast attack is used

    private val expectedDefense get() = 100

    fun fastAttackEnergyGain() = fast.energy / fast.duration.toDouble(SECONDS)

    fun chargedAttackDurationPerSecond(): Double =
        (charged.duration.toDouble(SECONDS) * fast.energy) /
                (charged.duration.toDouble(SECONDS) * fast.energy + fast.duration.toDouble(SECONDS) * charged.energy)

   fun effectiveFastAttackEnergyGain(): Double {
        return fastAttackEnergyGain() * (1 - chargedAttackDurationPerSecond())
   }

   fun chargedAttackDps(): Double {
       return damage(charged) * effectiveFastAttackEnergyGain() / charged.energy
   }

   fun damage(move: MoveData): Double {
       val stab = if (pokemon.isOfType(move.type)) 1.2 else 1.0
       val attack = calcStatValue(pokemon.baseAttack, individualPokemonStats.attack, individualPokemonStats.level)
       return (0.5 * move.power * attack * stab / expectedDefense) + 0.5 // gamepress formula
       //return floor(0.5 * attack.power * statValue(pokemon.baseAttack) * stab / expectedDefense) + 1; // original formula
   }
}
