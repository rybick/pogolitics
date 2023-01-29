package pogolitics.api

external interface FastMoveDto {
    val id: String
    val name: String
    val type: String
    val pvp: MoveStatsDto
    val pve: MoveStatsDto
}
