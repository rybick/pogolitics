package pogolitcs.controller

import pogolitcs.ModelAndView
import pogolitcs.view.MainPage
import kotlin.reflect.KClass

class MainPageController {

    fun get(): ModelAndView<Unit, KClass<MainPage>> {
        return ModelAndView(view = MainPage::class, model = Unit)
    }
}