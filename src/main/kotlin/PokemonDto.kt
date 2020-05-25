class PokemonDto(
    val id: Int,
    val name: String,
    val baseAttack: Int,
    val baseDefense: Int,
    val baseStamina: Int,
    val types: TypesDto,
    val moves: MovesDto
) {
    class TypesDto(val primary: String, val secondary: String)
    class MovesDto(val quick: Array<MoveDto>, val charged: Array<MoveDto>)
    class MoveDto(
        val id: Int,
        val name: String,
        val type: String,
        val legacy: Boolean,
        val exclusive: Boolean
    )
}

