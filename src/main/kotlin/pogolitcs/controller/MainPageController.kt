package pogolitcs.controller

import org.w3c.dom.url.URLSearchParams
import pogolitcs.ModelAndView
import pogolitcs.view.MainPage
import react.RProps
import kotlin.reflect.KClass

class MainPageController: Controller<RProps, Unit, Unit> {

    override fun getInitialState(url: String) = Unit

    override suspend fun get(props: RProps, params: URLSearchParams, state: Unit): ModelAndView<Unit, KClass<MainPage>> {
        return ModelAndView(view = MainPage::class, model = Unit)
    }
}