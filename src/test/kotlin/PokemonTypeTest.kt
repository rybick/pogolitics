import kotlin.test.*

class PokemonTypeTest {
    @Test
    fun against() {
        assertEquals(PokemonType.FIGHTING.against(PokemonType.NORMAL), PokemonType.Effectiveness.STRONG)
        assertEquals(PokemonType.FIGHTING.against(PokemonType.FIGHTING), PokemonType.Effectiveness.REGULAR)
        assertEquals(PokemonType.FIGHTING.against(PokemonType.POISON), PokemonType.Effectiveness.WEAK)
        assertEquals(PokemonType.FIGHTING.against(PokemonType.GHOST), PokemonType.Effectiveness.SUPER_WEAK)
    }

    @Test
    fun name() {
        assertEquals(PokemonType.FIGHTING.displayName, "Fighting")
    }
}