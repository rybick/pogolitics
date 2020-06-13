package pogolitcs.api

import kotlinx.coroutines.await
import kotlinx.coroutines.delay
import kotlin.browser.window

class Api() {
    private var pokemon: PokemonDto? = null
    private var fastMoves: Array<FastMoveDto>? = null
    private var chargedMoves: Array<ChargedMoveDto>? = null

    suspend fun fetchPokemon(id: Int): PokemonDto {
        if (pokemon == null || pokemon?.id != id) {
            pokemon = window.fetch("/data/pokemon/$id.json")
                    .await()
                    .json()
                    .await()
                    .unsafeCast<PokemonDto>()
            console.log(pokemon)
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
