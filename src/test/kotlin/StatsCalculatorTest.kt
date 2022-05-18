import pogolitics.controller.MoveSetStatsCalculator
import pogolitics.controller.MoveSetStatsCalculator.*
import pogolitics.controller.MoveSetStatsCalculator.MoveData
import pogolitics.model.PokemonType
import pogolitics.pvpTurns
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration
import kotlin.time.seconds

class StatsCalculatorTest {

    @Test
    fun testCalculator() {
        data class TestData(
                val calculator: MoveSetStatsCalculator,
                val expectedDps: Double,
                val expectedTtfa: Duration,
                val expectedMeanTimeBetweenAttacks: Duration,
                val expectedEffectiveFastAttackDps: Double
        )

        arrayOf( // given:
            TestData( // Mewtwo: Confusion + Psystrike
                calculator = MoveSetStatsCalculator(
                        pokemon = PokemonData(
                                baseAttack = 300,
                                baseDefense = 182,
                                baseStamina = 214,
                                types = PokemonTypes(PokemonType.PSYCHIC)
                        ),
                        fast = MoveData(power = 16, energy = 12, duration = 4.pvpTurns, type = PokemonType.PSYCHIC),
                        charged = MoveData(power = 90, energy = 45, duration = 1.pvpTurns, type = PokemonType.PSYCHIC),
                        individualPokemonStats = IndividualPokemonStats(40.0F, 15)
                ),
                expectedDps = 28.303131250000003,
                expectedTtfa = 8.00.seconds,
                expectedMeanTimeBetweenAttacks = 7.50.seconds,
                expectedEffectiveFastAttackDps = 11.436877500000001
            ),
            TestData( // Meltan: Thunder Bolt + Thunder Shock
                calculator = MoveSetStatsCalculator(
                        pokemon = PokemonData(
                                baseAttack = 226,
                                baseDefense = 190,
                                baseStamina = 264,
                                types = PokemonTypes(PokemonType.STEEL)
                        ),
                        fast = MoveData(power = 3, energy = 9, duration = 2.pvpTurns, type = PokemonType.ELECTRIC),
                        charged = MoveData(power = 90, energy = 55, duration = 1.pvpTurns, type = PokemonType.ELECTRIC),
                        individualPokemonStats = IndividualPokemonStats(40.0F, 15)
                ),
                expectedDps = 16.142919537815125,
                expectedTtfa = 7.00.seconds,
                expectedMeanTimeBetweenAttacks = 6.111.seconds,
                expectedEffectiveFastAttackDps = 3.1030486974789917
            ),
            TestData( // Dragonite: Dragon Tail + Hurricane
                calculator = MoveSetStatsCalculator(
                        pokemon = PokemonData(
                                baseAttack = 263,
                                baseDefense = 198,
                                baseStamina = 209,
                                types = PokemonTypes(PokemonType.DRAGON, PokemonType.FLYING)
                        ),
                        fast = MoveData(power = 9, energy = 10, duration = 3.pvpTurns, type = PokemonType.DRAGON),
                        charged = MoveData(power = 110, energy = 65, duration = 1.pvpTurns, type = PokemonType.FLYING),
                        individualPokemonStats = IndividualPokemonStats(40.0F, 15)
                ),
                expectedDps = 22.03611096585366,
                expectedTtfa = 10.50.seconds,
                expectedMeanTimeBetweenAttacks = 9.75.seconds,
                expectedEffectiveFastAttackDps = 7.840574965853658
            )
        ).forEach { // then:
            assertEquals(it.expectedDps, it.calculator.dps())
            assertEquals(it.expectedTtfa, it.calculator.timeToFirstAttack())
            assertEqualsToMillisecondPrecision(it.expectedMeanTimeBetweenAttacks, it.calculator.meanTimeBetweenAttacks())
            assertEquals(it.expectedEffectiveFastAttackDps, it.calculator.effectiveFastAttackDps())
        }
    }

    private fun assertEqualsToMillisecondPrecision(expected: Duration, actual: Duration) {
        assertEquals(expected.inMilliseconds.toInt(), actual.inMilliseconds.toInt())
    }
}
