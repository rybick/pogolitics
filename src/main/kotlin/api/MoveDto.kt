package api

data class MoveDto(
    val name: String,
    val type: String,
    val pvp: PvpMove,
    val pve: PveMove
) {
    class PvpMove(val power: Int, val energy: Int)
    class PveMove(val power: Int, val energy: Int, val duration: Int)
}
