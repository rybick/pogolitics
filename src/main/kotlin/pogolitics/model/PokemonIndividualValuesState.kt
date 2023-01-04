package pogolitics.model

// TODO use IVs class (turn it to wrapper of this and focus)
// TODO it can actually be read-only i think
data class PokemonIndividualValuesState(
    var level: Float?,
    var attack: Int,
    var defense: Int,
    var stamina: Int,
    var cp: Int?,
    var focus: SinglePokemonModel.InputElement? = null
) {
    constructor(ivs: IVs, level: Float): this(level, ivs.attack, ivs.defense, ivs.stamina, null)
}