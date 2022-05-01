package pogolitcs.api

data class FastMoveDto(
    val id: String,
    val name: String,
    val type: String,
    val pvp: MoveStatsDto,
    val pve: MoveStatsDto
)
