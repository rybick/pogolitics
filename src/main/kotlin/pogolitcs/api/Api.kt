package pogolitcs.api

import kotlinx.coroutines.await
import kotlinx.coroutines.delay
import pogolitcs.applicationRoot
import kotlin.browser.window

class Api() {
    private var pokemon: PokemonDto? = null
    private var fastMoves: Array<FastMoveDto>? = null
    private var chargedMoves: Array<ChargedMoveDto>? = null

    suspend fun fetchPokemon(id: String): PokemonDto {
        if (pokemon == null || pokemon?.id != id) {
            pokemon = fetchResource("/data/pokemon/$id.json")
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
            fastMoves = fetchResource("/data/attacks/fast.json")
                    .await()
                    .json()
                    .await()
                    .unsafeCast<Array<FastMoveDto>>()
        }
        return fastMoves!!
    }

    suspend fun fetchChargedMoves(): Array<ChargedMoveDto> {
        if (chargedMoves == null) {
            chargedMoves = fetchResource("/data/attacks/charged.json")
                    .await()
                    .json()
                    .await()
                    .unsafeCast<Array<ChargedMoveDto>>()
        }
        return chargedMoves!!
    }

    fun fetchResource(url: String) = window.fetch(applicationRoot + url)
}
