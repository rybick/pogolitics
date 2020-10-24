import pogolitcs.controller.CpCalculator
import pogolitcs.controller.CpCalculator.PokemonIv
import pogolitcs.controller.CpCalculator.PokemonData
import kotlin.test.Test
import kotlin.test.assertEquals

class CpCalculatorTest {

    val snorlax = PokemonData(190, 169, 330)
    val mewtwo = PokemonData(300, 182, 214)
    val groudon = PokemonData(270, 228, 205)
    val mamoswine = PokemonData(247, 146, 242)

    val ivMax = PokemonIv(15, 15, 15)

    @Test
    fun calcCp() {
        assertEquals(
            4178,
            CpCalculator(mewtwo, ivMax).calcCp(40.0F)
        )
        assertEquals(
            1749,
            CpCalculator(groudon, PokemonIv(14, 15, 13)).calcCp(15.0F)
        )
    }

    @Test
    fun calcLevel() {
        with(CpCalculator(snorlax, PokemonIv(12, 11, 13)).calcLevel(3000)) {
            assertEquals(37.0F, level)
            assertEquals(3000, cp)
            assertEquals(true, exact)
        }
        with(CpCalculator(snorlax, PokemonIv(12, 11, 13)).calcLevel(3001)) {
            assertEquals(37.0F, level)
            assertEquals(3000, cp)
            assertEquals(false, exact)
        }
        with(CpCalculator(snorlax, PokemonIv(15, 10, 15)).calcLevel(2544)) {
            assertEquals(27.5F, level)
            assertEquals(2500, cp)
            assertEquals(false, exact)
        }
        with(CpCalculator(snorlax, ivMax).calcLevel(3266)) {
            assertEquals(41.0F, level)
            assertEquals(3266, cp)
            assertEquals(true, exact)
        }
        with(CpCalculator(snorlax, ivMax).calcLevel(5000)) {
            assertEquals(41.0F, level)
            assertEquals(3266, cp)
            assertEquals(false, exact)
        }
        with(CpCalculator(snorlax, ivMax).calcLevel(10)) {
            assertEquals(1.0F, level)
            assertEquals(45, cp)
            assertEquals(false, exact)
        }
        with(CpCalculator(snorlax, ivMax).calcLevel(45)) {
            assertEquals(1.0F, level)
            assertEquals(45, cp)
            assertEquals(true, exact)
        }
    }

    @Test
    fun calcBestLeaguesCps() {
        with(CpCalculator(mamoswine, ivMax).calcBestUltraLeagueCp()) {
            //assertEquals(1500, great)
            assertEquals(2472, cp)
            assertEquals(26.0F, level)
        }
    }
}