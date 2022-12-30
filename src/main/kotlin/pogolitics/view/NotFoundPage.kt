package pogolitics.view

import csstype.TextAlign
import csstype.pct
import emotion.react.css
import pogolitics.PageRProps
import pogolitics.cssClass
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.html.ReactHTML.div
import react.dom.p
import styled.styledDiv

class NotFoundPage(props: PageRProps<NotFoundModel, Unit>) : RComponent<PageRProps<NotFoundModel, Unit>, RState>(props) {
    override fun RBuilder.render() = renderPage(null) {
        div {
            attrs.css(Styles.pageWrapper)
            styledDiv {
                attrs.css(BasicStylesheet.widgetHeader)
                + ("Page Not Found")
            }
            styledDiv {
                attrs.css(Styles.contentWrapper)
                p { +props.model.reason }
            }
        }
    }

    private object Styles {
        val pageWrapper = cssClass {
            padding = 10.pct
        }

        val contentWrapper = cssClass {
            paddingTop = StyleConstants.Padding.big
            paddingBottom = StyleConstants.Padding.big
            textAlign = TextAlign.center
        }
    }
}

data class NotFoundModel(val reason: String)