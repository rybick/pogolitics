package pogolitics.view

import kotlinx.css.*
import pogolitics.PageRProps
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.p
import styled.StyleSheet
import styled.css
import styled.styledDiv

class NotFoundPage(props: PageRProps<NotFoundModel, Unit>) : RComponent<PageRProps<NotFoundModel, Unit>, RState>(props) {
    override fun RBuilder.render() {
        styledDiv {
            css { +Styles.pageWrapper }
            styledDiv {
                css {
                    + BasicStylesheet.widgetHeader
                }
                + ("Page Not Found")
            }
            styledDiv {
                css {
                    + Styles.contentWrapper
                }
                p { +props.model.reason }
            }
        }
    }

    private object Styles: StyleSheet("ComponentStyles", isStatic = true) {
        val pageWrapper by css {
            padding = "10%"
        }

        val contentWrapper by css {
            paddingTop = StyleConstants.Padding.big
            paddingBottom = StyleConstants.Padding.big
            textAlign = TextAlign.center
        }
    }
}

data class NotFoundModel(val reason: String)