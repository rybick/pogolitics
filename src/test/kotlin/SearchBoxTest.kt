import js.core.jso
import pogolitics.model.PokemonEntry
import pogolitics.model.PokemonForm
import pogolitics.model.PokemonForm.Companion.DEFAULT
import pogolitics.view.component.PokemonFormEntry
import pogolitics.view.component.SearchBoxProps
import pogolitics.view.component.getFilteredData
import kotlin.test.Test
import kotlin.test.assertEquals

class SearchBoxTest {

    val BULBASAUR = "Bulbasaur"
    val PIKACHU = "Pikachu"
    val RAICHU = "Raichu"

    val POP_STAR = "Pop star".f
    val ROCK_STAR = "Rock star".f

    val _pokemonIndex = listOf(
        PokemonEntry(1, BULBASAUR, listOf(DEFAULT)),
        PokemonEntry(25, PIKACHU, listOf(DEFAULT, POP_STAR, ROCK_STAR)),
        PokemonEntry(26, RAICHU, listOf(DEFAULT)),
    )

    //@Test // TODO later
    fun filtersDataAndOrdersDefaultFormsFirst() {
        // Given
        val props = createProps()

        // When
        val result = props.getFilteredData("chu")

        // Then
        assertEquals(
            actual = result,
            expected = listOf(
                PokemonFormEntry(25, PIKACHU, 0, DEFAULT),
                PokemonFormEntry(26, RAICHU, 0, DEFAULT),
                PokemonFormEntry(25, PIKACHU, 1, POP_STAR),
                PokemonFormEntry(25, PIKACHU, 2, ROCK_STAR)
            )
        )
    }

    private fun createProps(): SearchBoxProps = jso {
        pokemonIndex = _pokemonIndex
    }

    private val String.f get() = PokemonForm.ofNullable(this, costume = false)
}