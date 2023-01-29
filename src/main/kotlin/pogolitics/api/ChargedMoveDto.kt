package pogolitics.api

external interface ChargedMoveDto {
    val id: String
    val name: String
    val type: String
    val pvp: PvpChargedMoveStatsDto
    val pve: MoveStatsDto
}
