@file:JsModule("bootstrap-switch-button-react")
@file:JsNonModule

package react

@JsName("default")
external val SwitchSelector: ComponentClass<SwitchSelectorProps>

external interface SwitchSelectorProps : Props {
    var onlabel: String // html
    var offlabel: String // html
    var size: String
    var onstyle: String
    var offstyle: String
    var style: String
    var width: Int
    var height: Int
}

