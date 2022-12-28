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
        private val form: PokemonForm?,
        val uniqueId: String,
    ) {
        fun toPokemonForm(): PokemonForm? = form
        val prettyNameOrDefault: String = form?.prettyName ?: "Default"
    }
}