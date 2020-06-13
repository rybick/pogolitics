package pogolitcs.model

data class SinglePokemonModel(
        val pokemon: PokemonStaticInfo,
        val stats: PokemonCalculatedStatistics,
        val moveSets: List<MoveSet>
) {
    data class PokemonStaticInfo(val id: Int, val name: String)
    data class PokemonCalculatedStatistics(val cp: Int)
}