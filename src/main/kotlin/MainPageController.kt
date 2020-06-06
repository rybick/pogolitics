import view.MainPage
import view.PokemonPage
import kotlin.reflect.KClass

class MainPageController {

    fun get(): ModelAndView<Unit, KClass<MainPage>> {
        return ModelAndView(view = MainPage::class, model = Unit)
    }
}