package pogolitcs.model

data class SinglePokemonModel(
        val pokemon: PokemonStaticInfo,
        val stats: PokemonIndividualStatistics,
        val moveSets: List<MoveSet>
) {
    data class PokemonStaticInfo(val id: Int, val name: String)
    @Deprecated("changing it now")
    data class PokemonIndividualStatistics1(val cp: Int, val level: Float, val attack: Int, val defense: Int, val stamina: Int)

    data class PokemonIndividualStatistics(
            val ivs: IVs,
            val currentStats: VariablePokemonStatistics,
            val bestGreatLeagueStats: VariablePokemonStatistics,
            val bestUltraLeagueStats: VariablePokemonStatistics
    )

    data class VariablePokemonStatistics(val cp: Int, val level: Float)
}