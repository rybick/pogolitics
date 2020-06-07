package pogolitcs.api

import kotlinx.coroutines.await
import kotlin.browser.window

// TODO later think of refactoring it to be class
object Api {
    suspend fun fetchPokemon(id: Int): PokemonDto {
        return window.fetch("/data/pokemon/$id.json")
            .await()
            .json()
            .await()
            .unsafeCast<PokemonDto>()
    }

    suspend fun fetchFastMoves(): Array<FastMoveDto> {
        return window.fetch("/data/attacks/fast.json")
            .await()
            .json()
            .await()
            .unsafeCast<Array<FastMoveDto>>()
    }

    suspend fun fetchChargedMoves(): Array<ChargedMoveDto> {
        return window.fetch("/data/attacks/charged.json")
            .await()
            .json()
            .await()
            .unsafeCast<Array<ChargedMoveDto>>()
    }
}
