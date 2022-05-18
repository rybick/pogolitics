package pogolitics.controller

import org.w3c.dom.url.URLSearchParams
import pogolitics.ControllerResult
import pogolitics.view.MainPage
import react.RProps
import kotlin.reflect.KClass

class MainPageController: Controller<RProps, Unit, Unit> {

    override fun getInitialState(url: String) = Unit

    override suspend fun get(props: RProps, params: URLSearchParams, state: Unit): ControllerResult<Unit, KClass<MainPage>> {
        return ControllerResult.modelAndView(view = MainPage::class, model = Unit)
    }
}