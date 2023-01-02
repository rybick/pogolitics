package pogolitics.view.component

import csstype.FontWeight
import csstype.None
import emotion.css.ClassName
import emotion.react.css
import pogolitics.view.Page
import pogolitics.view.StyleConstants
import pogolitics.view.pagePath
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span
import react.fc

val Breadcrumbs = fc<BreadcrumbsProps> { props ->
    props.page?.let { thePage ->
        div {
            attrs.css(BreadcrumbsStyles.breadcrumbsWrapper) {}
            thePage.getFullPath().forEachIndexed { index, page ->
                if (index > 0) {
                    span {
                        attrs.css(BreadcrumbsStyles.separator) {}
                        +"»"
                    }
                }
                span {
                    a {
                        attrs.href = pagePath(page)
                        +page.prettyName
                    }
                }
            }
        }
    }
}

interface BreadcrumbsProps: Props {
    var page: Page?
}

private object BreadcrumbsStyles {
    val breadcrumbsWrapper = ClassName {
        paddingLeft = StyleConstants.Padding.medium
        paddingBottom = StyleConstants.Padding.small
        backgroundColor = StyleConstants.Colors.primary.bg
        color = StyleConstants.Colors.primary.secondaryText
        fontSize = StyleConstants.Font.small
        a {
            color = StyleConstants.Colors.primary.secondaryText
            fontWeight = FontWeight.bold
            hover {
                color = StyleConstants.Colors.primaryHovered.secondaryText
                textDecoration = None.none
            }
        }
    }

    val separator = ClassName {
        marginLeft = StyleConstants.Padding.small
        marginRight = StyleConstants.Padding.small
    }
}