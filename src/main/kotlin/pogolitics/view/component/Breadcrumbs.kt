package pogolitics.view.component

import csstype.FontWeight
import csstype.None
import emotion.react.css
import pogolitics.cssClass
import pogolitics.view.Page
import pogolitics.view.StyleConstants
import pogolitics.view.pagePath
import react.Props
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.fc
import styled.styledSpan

val Breadcrumbs = fc<BreadcrumbsProps> { props ->
    props.page?.let { thePage ->
        div {
            attrs.css(BreadcrumbsStyles.breadcrumbsWrapper)
            thePage.getFullPath().forEachIndexed { index, page ->
                if (index > 0) {
                    styledSpan {
                        attrs.css(BreadcrumbsStyles.separator)
                        +"»"
                    }
                }
                styledSpan {
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
    val breadcrumbsWrapper = cssClass {
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

    val separator = cssClass {
        marginLeft = StyleConstants.Padding.small
        marginRight = StyleConstants.Padding.small
    }
}