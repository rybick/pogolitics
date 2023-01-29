package pogolitics.api

external interface PokemonIndexEntryDto {
    val uniqueId: String
    val pokedexNumber: Int
    val name: String
    val form: String?
    val formIndex: Int?
    val formCostume: Boolean?
}