import kotlinx.coroutines.MainScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import react.*
import kotlin.browser.window

external interface AppState : RState {
    var pokemonData: PokemonDto?
}

class App: RComponent<RProps, AppState>() {
    override fun RBuilder.render() {
        movesetsTable {
            values = state.pokemonData?.let(::getMovesets) ?: listOf()
        }
    }

    override fun AppState.init() {
        MainScope().launch {
            val data = fetchPokemonData(69)
            setState {
                pokemonData = data
            }
        }
    }

    private suspend fun fetchPokemonData(id: Int): PokemonDto {
        return window.fetch("/data/pokemon/$id.json")
            .await()
            .json()
            .await()
            .unsafeCast<PokemonDto>()
    }

    private fun getMovesets(pokemonDto: PokemonDto): List<Moveset> {
        return combinations(pokemonDto.moves.quick, pokemonDto.moves.charged) { quick, charged ->
            Moveset(
                quickAttack = Attack(PokemonType.fromString(quick.type), quick.name),
                chargedAttack = Attack(PokemonType.fromString(charged.type), charged.name),
                dps = 2.345F,
                timeToFirstAttack = 5.50F
            )
        }
    }

    private fun <T, U, O> combinations(arr1: Array<T>, arr2: Array<U>, mapper: (T, U) -> O): List<O> {
        return arr1.flatMap { a1 -> arr2.map { a2 -> mapper(a1, a2) } }
    }
}