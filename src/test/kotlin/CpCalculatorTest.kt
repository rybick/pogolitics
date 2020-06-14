import pogolitcs.controller.CpCalculator
import pogolitcs.controller.CpCalculator.IndividualPokemonStats
import pogolitcs.controller.CpCalculator.PokemonData
import kotlin.test.Test
import kotlin.test.assertEquals

class CpCalculatorTest {

    @Test
    fun test() {
        assertEquals(
            4178,
            CpCalculator(PokemonData(300, 182, 214), IndividualPokemonStats(40.0F, 15, 15, 15)).calcCp()
        )
        assertEquals(
            1749,
            CpCalculator(PokemonData(270, 228, 205), IndividualPokemonStats(15.0F, 14, 15, 13)).calcCp()
        )
    }

}