package pogolitics.controller

import pogolitics.model.PokemonType
import pogolitics.model.PokemonType.Effectiveness.REGULAR
import pogolitics.model.PokemonType.Effectiveness.STRONG
import pogolitics.model.PokemonType.Effectiveness.SUPER_WEAK
import pogolitics.model.PokemonType.Effectiveness.WEAK
import kotlin.math.ceil
import kotlin.time.Duration
import kotlin.time.DurationUnit.SECONDS

class MoveSetStatsCalculator(
        private val pokemon: PokemonData,
        private val fast: MoveData,
        private val charged: MoveData,
        private val individualPokemonStats: IndividualPokemonStats,
        private val target: Target = Target()
) {
    private companion object {
        const val expectedDefense = 100
    }

    data class PokemonData(
        val baseAttack: Int,
        val baseDefense: Int,
        val baseStamina: Int,
        val types: PokemonTypes
    ) {
        fun isOfType(type: PokemonType): Boolean {
            return types.primary == type || types.secondary == type
        }
    }

    data class PokemonTypes(val primary: PokemonType, val secondary: PokemonType? = null)

    data class Target(
        val defense: Int = expectedDefense,
        val types: PokemonTypes = PokemonTypes(PokemonType.NONE)
    )

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

    // DPS if only fast attack is used
    fun fastAttackDps(): Double = damage(fast) / fast.duration.toDouble(SECONDS)

    // all methods below could be private, but it's useful to be able to look into them (see MoveSetStatsCalculatorTest)
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
       val effectiveness = typeEffectivenessMultiplier(move.type, target.types)
       val attack = calcStatValue(pokemon.baseAttack, individualPokemonStats.attack, individualPokemonStats.level)
       return (0.5 * move.power * attack * stab * effectiveness / expectedDefense) + 0.5 // gamepress formula
       //return floor(0.5 * attack.power * statValue(pokemon.baseAttack) * stab * effectiveness / expectedDefense) + 1; // original formula
   }

    private fun typeEffectivenessMultiplier(moveType: PokemonType, targetTypes: PokemonTypes): Double {
        val primaryEffectiveness: Double = moveType.against(targetTypes.primary).toMultiplier()
        val secondaryEffectiveness: Double = targetTypes.secondary?.let { moveType.against(it).toMultiplier() } ?: 1.0
        return primaryEffectiveness * secondaryEffectiveness
    }

    private fun PokemonType.Effectiveness.toMultiplier(): Double = when(this) {
        STRONG -> 1.6
        REGULAR -> 1.0
        WEAK -> 0.625 // = (1 / 1.6)
        SUPER_WEAK -> 0.390625 // = (1 / (1.6)^2)
    }
}
