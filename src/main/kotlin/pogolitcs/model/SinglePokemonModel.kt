package pogolitcs.model

data class SinglePokemonModel(
        val pokemon: PokemonStaticInfo,
        val stats: PokemonIndividualStatistics,
        val moveSets: List<MoveSet>
) {
    data class PokemonStaticInfo(val id: Int, val name: String)
    data class PokemonIndividualStatistics(val cp: Int, val level: Float, val attack: Int, val defense: Int, val stamina: Int)
}