import pogolitics.controller.CpCalculator
import pogolitics.controller.CpCalculator.PokemonIv
import pogolitics.controller.CpCalculator.PokemonData
import kotlin.test.Test
import kotlin.test.assertEquals

class CpCalculatorTest {

    val snorlax = PokemonData(190, 169, 330)
    val mewtwo = PokemonData(300, 182, 214)
    val groudon = PokemonData(270, 228, 205)

    val ivMax = PokemonIv(15, 15, 15)

    @Test
    fun calcCp() {
        assertEquals(
            4178,
            CpCalculator(mewtwo, ivMax).calcStatisticsByLevel(40.0F).cp
        )
        assertEquals(
            1749,
            CpCalculator(groudon, PokemonIv(14, 15, 13)).calcStatisticsByLevel(15.0F).cp
        )
    }

    @Test
    fun calcLevel() {
        with(CpCalculator(snorlax, PokemonIv(12, 11, 13)).calcStatisticsByCp(3000)) {
            assertEquals(37.0F, level)
            assertEquals(3000, cp)
            assertEquals(true, matchingInputArgument)
        }
        with(CpCalculator(snorlax, PokemonIv(12, 11, 13)).calcStatisticsByCp(3001)) {
            assertEquals(37.0F, level)
            assertEquals(3000, cp)
            assertEquals(false, matchingInputArgument)
        }
        with(CpCalculator(snorlax, PokemonIv(15, 10, 15)).calcStatisticsByCp(2544)) {
            assertEquals(27.5F, level)
            assertEquals(2500, cp)
            assertEquals(false, matchingInputArgument)
        }
        with(CpCalculator(snorlax, ivMax).calcStatisticsByCp(3266)) {
            assertEquals(41.0F, level)
            assertEquals(3266, cp)
            assertEquals(true, matchingInputArgument)
        }
        with(CpCalculator(snorlax, ivMax).calcStatisticsByCp(5000)) {
            assertEquals(41.0F, level)
            assertEquals(3266, cp)
            assertEquals(false, matchingInputArgument)
        }
        with(CpCalculator(snorlax, ivMax).calcStatisticsByCp(10)) {
            assertEquals(1.0F, level)
            assertEquals(45, cp)
            assertEquals(false, matchingInputArgument)
        }
        with(CpCalculator(snorlax, ivMax).calcStatisticsByCp(45)) {
            assertEquals(1.0F, level)
            assertEquals(45, cp)
            assertEquals(true, matchingInputArgument)
        }
    }
}