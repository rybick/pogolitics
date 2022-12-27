package pogolitics.model

enum class BattleMode {
    PVP, PVE;

    companion object {
        fun fromString(value: String) = BattleMode.valueOf(value.uppercase())
        val default = PVP
    }
}