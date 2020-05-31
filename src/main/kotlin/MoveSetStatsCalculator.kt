import kotlin.js.Math
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
class MoveSetStatsCalculator(private val pokemon: PokemonData, private val fast: Attack, private val charged: Attack) {
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

    data class Attack constructor(val power: Int, val energy: Int, val duration: Duration, val type: PokemonType)

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
    private fun fastAttackDps(): Double = damage(fast) / fast.duration.inSeconds

    private val expectedDefense get() = 100

    private fun fastAttackEnergyGain() = fast.energy / fast.duration.inSeconds

    private fun chargedAttackDurationPerSecond(): Double =
        (charged.duration.inSeconds * fast.energy) /
                (charged.duration.inSeconds * fast.energy + fast.duration.inSeconds * charged.energy)

    private fun effectiveFastAttackEnergyGain(): Double {
        return fastAttackEnergyGain() * (1 - chargedAttackDurationPerSecond())
    }

    private fun chargedAttackDps(): Double {
        return damage(charged) * effectiveFastAttackEnergyGain() / charged.energy
    }

    private fun damage(attack: Attack): Double {
        val stab = if (pokemon.isOfType(attack.type)) 1.2 else 1.0
        return (0.5 * attack.power * statValue(pokemon.baseAttack) * stab / expectedDefense) + 0.5 // gamepress formula
        //return floor(0.5 * attack.power * statValue(pokemon.baseAttack) * stab / expectedDefense) + 1; // original formula
    }

    private fun statValue(baseStat: Int, iv: Int = 15, level: Float = 40F): Double {
        return getCPMultiplayer(level) * (baseStat + iv)
    }

    private fun getCPMultiplayer(level: Float) = CPMultiplayer[level]
}
