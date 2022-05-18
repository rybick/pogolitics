package pogolitics.api

data class PokemonDto(
    val uniqueId: String,
    val pokedexNumber: Int,
    val name: String,
    val baseAttack: Int,
    val baseDefense: Int,
    val baseStamina: Int,
    val types: TypesDto,
    val moves: MovesDto
) {
    data class TypesDto(val primary: String, val secondary: String?)
    class MovesDto(val quick: Array<MoveDto>, val charged: Array<MoveDto>)
    data class MoveDto(
        val id: String,
        val elite: Boolean
    )
}

