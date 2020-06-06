import react.RComponent
import react.RProps
import react.RState
import kotlin.reflect.KClass

data class ModelAndView<
    M,
    out V: KClass<out RComponent<out PageRProps<M>, out RState>>
>(
    val model: M,
    val view: V
) {

}

interface PageRProps<T> : RProps {
    var model: T
}

abstract class Tmp<X:KClass<out A>>(val x:X)

class TmpB(a:KClass<B>): Tmp<KClass<B>>(a)

fun <X:A> x(y: Tmp<KClass<X>>): KClass<X> {
    return y.x
}

interface A {}
class B(val a: Int): A

fun a() {
   x(TmpB(B::class))
}