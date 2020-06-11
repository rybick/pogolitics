package pogolitcs.view

import pogolitcs.model.PokemonIndividualValues
import react.*

class IVStatsWidget(props: IVStatsWidgetRProps) : RComponent<IVStatsWidgetRProps, RState>(props) {

    override fun RBuilder.render() {
        ivBar {
            name = "Attack"
            iv = props.ivs.attack
            onChange = { value ->
                props.onChange(props.ivs.apply { attack = value })
            }
        }
        ivBar {
            name = "Defense"
            iv = props.ivs.defense
            onChange = { value ->
                props.onChange(props.ivs.apply { defense = value })
            }
        }
        ivBar {
            name = "HP"
            iv = props.ivs.stamina
            onChange = { value ->
                setState {}
                props.onChange(props.ivs.apply { stamina = value })
            }
        }
    }
}

external interface IVStatsWidgetRProps: RProps {
    var ivs: PokemonIndividualValues;
    var onChange: (PokemonIndividualValues) -> Unit
}

fun RBuilder.ivStatsWidget(handler: IVStatsWidgetRProps.() -> Unit): ReactElement {
    return child(IVStatsWidget::class) {
        this.attrs(handler)
    }
}