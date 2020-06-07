import api.Api
import api.ChargedMoveDto
import api.FastMoveDto
import api.PokemonDto
import view.PokemonPage
import kotlin.reflect.KClass

class PokemonController(val api: Api) {

    data class Model(
        val pokemonData: PokemonDto,
        val fastMoves: Array<FastMoveDto>,
        val chargedMoves: Array<ChargedMoveDto>
    )

    suspend fun get(id: Int): ModelAndView<Model, KClass<PokemonPage>> {
        return ModelAndView(
            view = PokemonPage::class,
            model = Model(api.fetchPokemon(id), api.fetchFastMoves(), api.fetchChargedMoves())
        )
    }
}