package pogolitics.api

import kotlinx.coroutines.await
import pogolitics.applicationRoot
import kotlin.browser.window

class Api() {
    private var index: Array<PokemonIndexEntryDto>? = null
    private var pokemon: PokemonDto? = null
    private var fastMoves: Array<FastMoveDto>? = null
    private var chargedMoves: Array<ChargedMoveDto>? = null

    suspend fun fetchPokemonIndex(): Array<PokemonIndexEntryDto> {
        if (index == null) {
            index = fetchResource("/data/pokemon/index.json")
                .await()
                .json()
                .await()
                .unsafeCast<Array<PokemonIndexEntryDto>>()
        }
        return index!!
    }

    suspend fun fetchPokemon(uniqueId: String): PokemonDto {
        if (pokemon == null || pokemon?.uniqueId != uniqueId) {
            pokemon = fetchResource("/data/pokemon/$uniqueId.json")
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
