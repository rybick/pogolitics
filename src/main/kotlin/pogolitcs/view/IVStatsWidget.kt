package pogolitcs.view

import react.*

class IVStatsWidget(props: IVStatsWidgetRProps) : RComponent<IVStatsWidgetRProps, IVStatsWidgetRState>(props) {

    override fun RBuilder.render() {
        if (state.ivs == null) { // TODO tmp
            state.ivs = props.ivs.copy()
        }
        ivBar {
            name = "Attack"
            console.log("aa", state.ivs)
            iv = state.ivs!!.attack
            onChange = {
                setState { ivs!!.attack = it }
            }
        }
        ivBar {
            name = "Defense"
            iv = state.ivs!!.defense
            onChange = {
                setState { ivs!!.defense = it }
            }
        }
        ivBar {
            name = "HP"
            iv = state.ivs!!.stamina
            onChange = {
                setState { ivs!!.stamina = it }
            }
        }
    }
}

external interface IVStatsWidgetRProps: RProps {
    var ivs: IVStats;
}

external interface IVStatsWidgetRState: RState { // TODO pull it later
    var ivs: IVStats?
}

data class IVStats(var attack:Int, var defense:Int, var stamina:Int)

fun RBuilder.ivStatsWidget(handler: IVStatsWidgetRProps.() -> Unit): ReactElement {
    return child(IVStatsWidget::class) {
        this.attrs(handler)
    }
}