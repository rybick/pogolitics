@file:JsModule("react-search-input")
@file:JsNonModule

package react

@JsName("default")
external val SearchInput: FC<SearchBoxProps>

external interface SearchBoxProps : Props {
    var className: String
    var onChange: (newValue: String) -> Unit
    var filterKeys: Array<String>
    var throttle: Int
    var caseSensitive: Boolean
    var fuzzy: Boolean
    var sortResults: Boolean
    var value: String
}