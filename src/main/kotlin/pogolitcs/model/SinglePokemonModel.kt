package pogolitcs.model

data class SinglePokemonModel(
    val pokemon: Pokemon,
    val moveSets: List<MoveSet>
) {
    data class Pokemon(val id: Int, val name: String)
}