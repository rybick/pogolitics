package pogolitics.model

data class PokemonEntry(
    val pokedexNumber: Int,
    val name: String,
    val forms: List<PokemonForm>
)