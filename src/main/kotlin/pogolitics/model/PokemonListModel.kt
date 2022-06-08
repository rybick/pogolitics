package pogolitics.model

data class PokemonListModel(
    val pokemon: List<PokemonEntry>
) {
    data class PokemonEntry(
        val pokedexNumber: Int,
        val name: String,
        val forms: List<Form>
    )

    data class Form(
        val uniqueId: String,
        val name: String?,
        val prettyName: String
    )
}