package pogolitics.controller

import org.w3c.dom.url.URLSearchParams
import pogolitics.ControllerResult
import pogolitics.view.HomePage
import react.router.Params
import kotlin.reflect.KClass

class HomePageController: Controller<Unit, Unit> {

    override fun getInitialState(url: String) {}

    override suspend fun get(props: Params, params: URLSearchParams, state: Unit): ControllerResult<Unit, KClass<HomePage>> {
        return ControllerResult.modelAndView(view = HomePage::class, model = Unit)
    }
}