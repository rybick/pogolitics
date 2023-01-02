package pogolitics.view

import csstype.TextAlign
import csstype.pct
import emotion.css.ClassName
import emotion.react.css
import pogolitics.PageRProps
import react.Component
import react.RState
import react.ReactNode
import react.attrs
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p

class NotFoundPage(props: PageRProps<NotFoundModel, Unit>) : Component<PageRProps<NotFoundModel, Unit>, RState>(props) {

    override fun render(): ReactNode = renderPage(null) {
        div {
            css(Styles.pageWrapper) {}
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