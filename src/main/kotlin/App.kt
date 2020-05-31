import api.Api
import api.ChargedMoveDto
import api.FastMoveDto
import api.PokemonDto
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.*

external interface AppState : RState {
    var pokemonData: PokemonDto?
    var fastMoves: Array<FastMoveDto>?
    var chargedMoves: Array<ChargedMoveDto>?
}

fun AppState.isReady() = this.pokemonData != null && this.fastMoves != null && this.chargedMoves != null

class App: RComponent<RProps, AppState>() {
    override fun RBuilder.render() {
        moveSetsTable {
            values = if(state.isReady()) getMoveSets(state) else listOf()
        }
    }

    override fun AppState.init() {
        MainScope().launch {
            val _pokemon = Api.fetchPokemon(150)
            val _fastMoves = Api.fetchFastMoves()
            val _chargedMoves = Api.fetchChargedMoves()
            setState {
                pokemonData = _pokemon
                fastMoves = _fastMoves
                chargedMoves = _chargedMoves
            }
        }
    }

    private fun getMoveSets(state: AppState): List<MoveSet> {
        return MoveSetsTableDataMapper(state.pokemonData!!, state.fastMoves!!, state.chargedMoves!!)
            .getData()
    }
}
