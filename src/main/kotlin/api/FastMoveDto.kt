package api

data class FastMoveDto(
    val name: String,
    val type: String,
    val pvp: MoveStatsDto,
    val pve: MoveStatsDto
)
