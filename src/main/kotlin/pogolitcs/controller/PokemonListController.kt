package pogolitcs.controller

import org.w3c.dom.url.URLSearchParams
import pogolitcs.ControllerResult
import pogolitcs.view.MainPage
import pogolitcs.view.PokemonListPage
import react.RProps
import kotlin.reflect.KClass

class PokemonListController: Controller<RProps, Unit, Unit> {

    override fun getInitialState(url: String) = Unit

    override suspend fun get(props: RProps, params: URLSearchParams, state: Unit): ControllerResult<Unit, KClass<PokemonListPage>> {
        return ControllerResult.modelAndView(view = PokemonListPage::class, model = Unit)
    }
}