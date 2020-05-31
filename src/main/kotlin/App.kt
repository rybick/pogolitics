import api.Api
import api.MoveDto
import api.PokemonDto
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.*
import kotlin.time.ExperimentalTime

external interface AppState : RState {
    var pokemonData: PokemonDto?
    var fastMoves: Array<MoveDto>?
    var chargedMoves: Array<MoveDto>?
}

fun AppState.isReady() = this.pokemonData != null && this.fastMoves != null && this.chargedMoves != null

@ExperimentalTime
class App: RComponent<RProps, AppState>() {
    override fun RBuilder.render() {
        moveSetsTable {
            values = if(state.isReady()) getMoveSets(state) else listOf()
        }
    }

    override fun AppState.init() {
        MainScope().launch {
            val _pokemon = Api.fetchPokemon(150)
            val _fastMoves = Api.fetchQuickMoves()
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
