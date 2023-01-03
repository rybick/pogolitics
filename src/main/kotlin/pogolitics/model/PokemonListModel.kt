package pogolitics.model

data class PokemonListModel(
    val pokemon: List<PokemonEntry>
) {
    data class PokemonEntry(
        val pokedexNumber: Int,
        val name: String,
        val forms: List<PokemonForm>
    )
}