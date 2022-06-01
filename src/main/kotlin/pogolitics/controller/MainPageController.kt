package pogolitics.controller

import org.w3c.dom.url.URLSearchParams
import pogolitics.ControllerResult
import pogolitics.view.MainPage
import react.RProps
import react.router.Params
import kotlin.reflect.KClass

class MainPageController: Controller<Unit, Unit> {

    override fun getInitialState(url: String) {}

    override suspend fun get(props: Params, params: URLSearchParams, state: Unit): ControllerResult<Unit, KClass<MainPage>> {
        return ControllerResult.modelAndView(view = MainPage::class, model = Unit)
    }
}