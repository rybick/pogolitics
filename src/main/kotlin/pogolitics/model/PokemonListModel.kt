package pogolitics.model

data class PokemonListModel(
    val pokemon: List<PokemonEntry>
) {
    data class PokemonEntry(
        val uniqueId: String,
        val pokedexNumber: Int,
        val name: String,
        val form: String?
    )
}