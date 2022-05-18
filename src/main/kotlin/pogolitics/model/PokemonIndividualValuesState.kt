package pogolitics.model

// TODO use IVs class
// TODO it can actually be read-only i think
data class PokemonIndividualValuesState(
    var level: Float?,
    var attack: Int,
    var defense: Int,
    var stamina: Int,
    var cp: Int?
) {
    constructor(ivs: IVs, level: Float): this(level, ivs.attack, ivs.defense, ivs.stamina, null)
}