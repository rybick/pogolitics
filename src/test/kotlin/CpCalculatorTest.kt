import pogolitcs.controller.CpCalculator
import pogolitcs.controller.CpCalculator.PokemonIv
import pogolitcs.controller.CpCalculator.PokemonData
import kotlin.test.Test
import kotlin.test.assertEquals

class CpCalculatorTest {

    @Test
    fun calcCp() {
        assertEquals(
            4178,
            CpCalculator(PokemonData(300, 182, 214), PokemonIv(15, 15, 15)).calcCp(40.0F)
        )
        assertEquals(
            1749,
            CpCalculator(PokemonData(270, 228, 205), PokemonIv(14, 15, 13)).calcCp(15.0F)
        )
    }

    @Test
    fun calcLevel() {
        with(CpCalculator(PokemonData(190, 169, 330), PokemonIv(12, 11, 13)).calcLevel(3000)) {
            assertEquals(37.0F, level)
            assertEquals(3000, cp)
            assertEquals(true, exact)
        }
        with(CpCalculator(PokemonData(190, 169, 330), PokemonIv(12, 11, 13)).calcLevel(3001)) {
            assertEquals(37.0F, level)
            assertEquals(3000, cp)
            assertEquals(false, exact)
        }
        with(CpCalculator(PokemonData(190, 169, 330), PokemonIv(15, 10, 15)).calcLevel(2544)) {
            assertEquals(27.5F, level)
            assertEquals(2500, cp)
            assertEquals(false, exact)
        }
        with(CpCalculator(PokemonData(190, 169, 330), PokemonIv(15, 15, 15)).calcLevel(3266)) {
            assertEquals(41.0F, level)
            assertEquals(3266, cp)
            assertEquals(true, exact)
        }
        with(CpCalculator(PokemonData(190, 169, 330), PokemonIv(15, 15, 15)).calcLevel(5000)) {
            assertEquals(41.0F, level)
            assertEquals(3266, cp)
            assertEquals(false, exact)
        }
        with(CpCalculator(PokemonData(190, 169, 330), PokemonIv(15, 15, 15)).calcLevel(10)) {
            assertEquals(1.0F, level)
            assertEquals(45, cp)
            assertEquals(false, exact)
        }
        with(CpCalculator(PokemonData(190, 169, 330), PokemonIv(15, 15, 15)).calcLevel(45)) {
            assertEquals(1.0F, level)
            assertEquals(45, cp)
            assertEquals(true, exact)
        }
    }
}