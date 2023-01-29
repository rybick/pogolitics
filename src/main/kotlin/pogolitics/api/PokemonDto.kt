package pogolitics.api

external interface PokemonDto {
    val uniqueId: String
    val pokedexNumber: Int
    val name: String
    val baseAttack: Int
    val baseDefense: Int
    val baseStamina: Int
    val types: TypesDto
    val moves: MovesDto

    interface TypesDto {
        val primary: String
        val secondary: String?
    }

    interface MovesDto {
        val quick: Array<MoveDto>
        val charged: Array<MoveDto>
    }

    interface MoveDto {
        val id: String
        val elite: Boolean
    }
}

