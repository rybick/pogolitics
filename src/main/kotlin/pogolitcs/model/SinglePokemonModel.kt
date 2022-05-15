package pogolitcs.model

data class SinglePokemonModel(
        val pokemon: PokemonStaticInfo,
        val stats: PokemonIndividualStatistics,
        val moveSets: List<MoveSet>
) {
    data class PokemonStaticInfo(
        val uniqueId: String,
        val pokedexNumber: Int,
        val name: String,
        val baseAttack: Int,
        val baseDefense: Int,
        val baseStamina: Int,
        val hardiness: Double
    ) {
        val familyId: Int get() = pokedexNumber
    }

    data class PokemonIndividualStatistics(
        val ivs: IVs,
        val currentStats: VariablePokemonStatistics,
        val bestGreatLeagueStats: VariablePokemonStatistics,
        val bestUltraLeagueStats: VariablePokemonStatistics,
        val bestStatsWithoutBoost: VariablePokemonStatistics
    )

    data class VariablePokemonStatistics(
        val cp: Int,
        val level: Float,
        val attack : Double,
        val defense : Double,
        val stamina : Double,
        val hardiness: Double
    )
}