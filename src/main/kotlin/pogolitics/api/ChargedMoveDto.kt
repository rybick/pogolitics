package pogolitics.api

data class ChargedMoveDto(
    val id: String,
    val name: String,
    val type: String,
    val pvp: PvpChargedMoveStatsDto,
    val pve: MoveStatsDto
)
