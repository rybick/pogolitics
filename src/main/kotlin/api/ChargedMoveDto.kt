package api

data class ChargedMoveDto(
    val name: String,
    val type: String,
    val pvp: PvpChargedMoveStatsDto,
    val pve: MoveStatsDto
)
