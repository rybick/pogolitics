package pogolitcs.api

import kotlinx.coroutines.await
import kotlinx.coroutines.delay
import kotlin.browser.window

class Api() {
    private var pokemon: PokemonDto? = null
    private var fastMoves: Array<FastMoveDto>? = null
    private var chargedMoves: Array<ChargedMoveDto>? = null

    init {
        console.log("initted API")
    }

    suspend fun fetchPokemon(id: Int): PokemonDto {
        console.log(pokemon?.id, id, pokemon?.id != id, pokemon == null || pokemon?.id != id)
        if (pokemon == null || pokemon?.id.toString() != id.toString()) { // TODO later civilized json parsing
            pokemon = window.fetch("/data/pokemon/$id.json")
                    .await()
                    .json()
                    .await()
                    .unsafeCast<PokemonDto>()
        }
        return pokemon!!
    }

    suspend fun fetchFastMoves(): Array<FastMoveDto> {
        if (fastMoves == null) {
            fastMoves = window.fetch("/data/attacks/fast.json")
                    .await()
                    .json()
                    .await()
                    .unsafeCast<Array<FastMoveDto>>()
        }
        return fastMoves!!
    }

    suspend fun fetchChargedMoves(): Array<ChargedMoveDto> {
        if (chargedMoves == null) {
            chargedMoves = window.fetch("/data/attacks/charged.json")
                    .await()
                    .json()
                    .await()
                    .unsafeCast<Array<ChargedMoveDto>>()
        }
        return chargedMoves!!
    }
}
