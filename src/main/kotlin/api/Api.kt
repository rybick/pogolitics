package api

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

    suspend fun fetchChargedMoves(): Array<MoveDto> {
        return window.fetch("/data/attacks/charged.json")
            .await()
            .json()
            .await()
            .unsafeCast<Array<MoveDto>>()
    }

    suspend fun fetchQuickMoves(): Array<MoveDto> {
        return window.fetch("/data/attacks/quick.json")
            .await()
            .json()
            .await()
            .unsafeCast<Array<MoveDto>>()
    }
}
