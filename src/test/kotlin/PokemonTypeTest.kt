import kotlin.test.*

class PokemonTypeTest {
    @Test
    fun against() {
        assertEquals(PokemonType.Effectiveness.STRONG, PokemonType.FIGHTING.against(PokemonType.NORMAL))
        assertEquals(PokemonType.Effectiveness.REGULAR, PokemonType.FIGHTING.against(PokemonType.FIGHTING))
        assertEquals(PokemonType.Effectiveness.WEAK, PokemonType.FIGHTING.against(PokemonType.POISON))
        assertEquals(PokemonType.Effectiveness.SUPER_WEAK, PokemonType.FIGHTING.against(PokemonType.GHOST))
    }

    @Test
    fun name() {
        assertEquals("Fighting", PokemonType.FIGHTING.displayName)
    }
}
