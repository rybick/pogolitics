import pogolitics.controller.MoveSetStatsCalculator
import pogolitics.controller.MoveSetStatsCalculator.IndividualPokemonStats
import pogolitics.controller.MoveSetStatsCalculator.MoveData
import pogolitics.controller.MoveSetStatsCalculator.PokemonData
import pogolitics.controller.MoveSetStatsCalculator.PokemonTypes
import pogolitics.controller.MoveSetStatsCalculator.Target
import pogolitics.model.PokemonType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class MoveSetStatsCalculatorTest {

    val SALAMANCE = PokemonData(
        baseAttack = 277,
        baseDefense = 168,
        baseStamina = 216,
        types = PokemonTypes(PokemonType.DRAGON, PokemonType.FLYING)
    )

    val MACHAMP = PokemonData(
        baseAttack = 234,
        baseDefense = 159,
        baseStamina = 207,
        types = PokemonTypes(PokemonType.FIGHTING)
    )

    val FAST_DRAGON_TAIL = MoveData(
        power = 15,
        energy = 9,
        duration = 1100.milliseconds,
        type = PokemonType.DRAGON,
    )

    val FAST_COUNTER = MoveData(
        power = 12,
        energy = 8,
        duration = 900.milliseconds,
        type = PokemonType.FIGHTING
    )

    val CHARGED_CLOSE_COMBAT = MoveData(
        power = 100,
        energy = 100,
        duration = 2300.milliseconds,
        type = PokemonType.FIGHTING
    )

    val CHARGED_OUTRAGE = MoveData(
        power = 110,
        energy = 50,
        duration = 3900.milliseconds,
        type = PokemonType.DRAGON
    )

    val CHARGED_OUTRAGE_twice_slower_and_stronger = MoveData(
        power = 220,
        energy = 100,
        duration = 7800.milliseconds,
        type = PokemonType.DRAGON,
    )

    val CHARGED_DRACO_METEOR = MoveData(
        power = 150,
        energy = 100,
        duration = 3600.milliseconds,
        type = PokemonType.DRAGON,
    )

    val CHARGED_DRACO_METEOR_longer = MoveData(
        power = 150,
        energy = 100,
        duration = 7800.milliseconds,
        type = PokemonType.DRAGON,
    )

    val LEGACY_MAX = IndividualPokemonStats(40.0f, 15)

    @Test
    fun testOutrage() {
        val outrage = salamanceWithDragonTailAnd(CHARGED_OUTRAGE)

        assertEquals(outrage.toCalculator().dps(), calcDpsAfterNTurns(100_000, outrage), absoluteTolerance = 0.001)
    }

    @Test
    fun testDracoMeteor() {
        val dracoMeteor = salamanceWithDragonTailAnd(CHARGED_DRACO_METEOR)

        assertEquals(dracoMeteor.toCalculator().dps(), calcDpsAfterNTurns(100_000, dracoMeteor), absoluteTolerance = 0.001)
    }

    @Test
    fun testEffectiveness() {
        fun fightingDpsAgainst(types: PokemonTypes) =
            machampWithFightingAttacks().against(types.toTarget()).toCalculator().dps()

        val baseDps = fightingDpsAgainst(PokemonTypes(PokemonType.DRAGON))
        val dpsEffective = fightingDpsAgainst(PokemonTypes(PokemonType.ROCK))
        val dpsDoubleEffective = fightingDpsAgainst(PokemonTypes(PokemonType.ROCK, PokemonType.ICE))
        val dpsNotEffective = fightingDpsAgainst(PokemonTypes(PokemonType.FLYING))
        val dpsImmune = fightingDpsAgainst(PokemonTypes(PokemonType.GHOST))
        val dpsDoubleNotEffective = fightingDpsAgainst(PokemonTypes(PokemonType.FLYING, PokemonType.FAIRY))
        val dpsNotEffectiveAndImmune = fightingDpsAgainst(PokemonTypes(PokemonType.FLYING, PokemonType.GHOST))

        // the formula is lineal, but the function does not exactly goes through (0, 0)
        // so just multiplying will be ignificantly off but still should be close
        assertEquals(1.6 * baseDps, dpsEffective, absoluteTolerance = 0.8)
        assertEquals(1.6 * 1.6 * baseDps, dpsDoubleEffective, absoluteTolerance = 1.28)
        assertEquals(baseDps / 1.6, dpsNotEffective, absoluteTolerance = 0.4)
        assertEquals(baseDps / 1.6 / 1.6, dpsImmune, absoluteTolerance = 0.4)
        assertEquals(baseDps / 1.6 / 1.6, dpsDoubleNotEffective, absoluteTolerance = 0.4)
        assertEquals(baseDps / 1.6 / 1.6 / 1.6, dpsNotEffectiveAndImmune, absoluteTolerance = 0.4)
    }

    // This is not really a test, but a piece of code that allows to take a deeper look into the calculations
//    @Test
    fun debugDpsCalculations() {
        val outrage = salamanceWithDragonTailAnd(CHARGED_OUTRAGE)
        val outrageB = salamanceWithDragonTailAnd(CHARGED_OUTRAGE_twice_slower_and_stronger)
        val dracoMeteor = salamanceWithDragonTailAnd(CHARGED_DRACO_METEOR)
        val dracoMeteorB = salamanceWithDragonTailAnd(CHARGED_DRACO_METEOR_longer)

        printResult("Outrage", outrage)
        printResult("Outrage but longer", outrageB)
        printResult("Draco Meteor", dracoMeteor)
        printResult("Draco Meteor but longer", dracoMeteorB)
    }

    private fun calcDpsAfterNTurns(numberOfTurns: Int, input: CalculatorInput): Double {
        val calculator = input.toCalculator()
        var damage: Double = 0.0
        var energy: Double = 0.0
        var time: Duration = 0.0.seconds
        for (i in 1..numberOfTurns) {
            if (energy >= input.charged.energy) {
                damage += calculator.damage(input.charged)
                energy -= input.charged.energy
                time += input.charged.duration
            } else {
                damage += calculator.damage(input.fast)
                energy += input.fast.energy
                time += input.fast.duration
            }
        }
        return (damage / time.inWholeMilliseconds) * 1000
    }

    private fun printResult(label: String, input: CalculatorInput) {
        val calc = input.toCalculator()
        println(label)
        print("dps", calc.dps())
        print("ttfa", calc.timeToFirstAttack())
        print("mtba", calc.meanTimeBetweenAttacks())
        print("ef-dps", calc.effectiveFastAttackDps())
        print("f-dps", calc.fastAttackDps())
        print("c-dps", calc.chargedAttackDps())
        print("f-eg", calc.fastAttackEnergyGain())
        print("ef-eg", calc.effectiveFastAttackEnergyGain())
        print("c-dups", calc.chargedAttackDurationPerSecond())
        print("dps10", calcDpsAfterNTurns(10, input))
        print("dps100", calcDpsAfterNTurns(100, input))
        print("dps1000", calcDpsAfterNTurns(1000, input))
        print("dps10000", calcDpsAfterNTurns(10_000, input))
        print("dps100000", calcDpsAfterNTurns(100_000, input))
        println()
    }

    private fun print(label: String, value: Any) {
        print("$label: $value | ")
    }

    private fun salamanceWithDragonTailAnd(chargedAttack: MoveData) =
        CalculatorInput(
            pokemon = SALAMANCE,
            fast = FAST_DRAGON_TAIL,
            charged = chargedAttack,
            individualPokemonStats = LEGACY_MAX
        )

    private fun machampWithFightingAttacks() =
        CalculatorInput(
            pokemon = MACHAMP,
            fast = FAST_COUNTER,
            charged = CHARGED_CLOSE_COMBAT,
            individualPokemonStats = LEGACY_MAX
        )

    private data class CalculatorInput(
        val pokemon: PokemonData,
        val fast: MoveData,
        val charged: MoveData,
        val individualPokemonStats: IndividualPokemonStats,
        val target: Target = Target()
    ) {
        fun toCalculator() = MoveSetStatsCalculator(pokemon, fast, charged, individualPokemonStats, target)

        fun against(target: Target) =
            CalculatorInput(
                pokemon = pokemon,
                fast = fast,
                charged = charged,
                individualPokemonStats = individualPokemonStats,
                target = target
            )
    }

    fun PokemonTypes.toTarget() = Target(types = this)
}