package pogolitics.view

import csstype.TextAlign
import csstype.pct
import emotion.css.ClassName
import emotion.react.css
import pogolitics.PageRProps
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.html.ReactHTML.div
import react.dom.p

class NotFoundPage(props: PageRProps<NotFoundModel, Unit>) : RComponent<PageRProps<NotFoundModel, Unit>, RState>(props) {
    override fun RBuilder.render() = renderPage(null) {
        div {
            attrs.css(Styles.pageWrapper) {}
            div {
                attrs.css(BasicStylesheet.widgetHeader) {}
                + ("Page Not Found")
            }
            div {
                attrs.css(Styles.contentWrapper) {}
                p { +props.model.reason }
            }
        }
    }

    private object Styles {
        val pageWrapper = ClassName {
            padding = 10.pct
        }

        val contentWrapper = ClassName {
            paddingTop = StyleConstants.Padding.big
            paddingBottom = StyleConstants.Padding.big
            textAlign = TextAlign.center
        }
    }
}

data class NotFoundModel(val reason: String)