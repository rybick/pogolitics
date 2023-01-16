package pogolitics.controller

import org.w3c.dom.url.URLSearchParams
import pogolitics.ControllerResult
import pogolitics.model.HomePageModel
import pogolitics.view.HomePage
import react.router.Params
import kotlin.reflect.KClass

class HomePageController(private val pokemonIndexService: PokemonIndexService): Controller<HomePageModel, Unit> {

    override fun getInitialState(url: String) {}

    override suspend fun get(
        props: Params,
        params: URLSearchParams,
        state: Unit
    ): ControllerResult<*, *> {
        return ControllerResult.modelAndView(
            view = HomePage::class,
            model = HomePageModel(pokemonIndexService.getPokemonList())
        )
    }
}