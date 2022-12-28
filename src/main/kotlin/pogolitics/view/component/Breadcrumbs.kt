package pogolitics.view.component

import kotlinx.css.*
import pogolitics.view.Page
import pogolitics.view.StyleConstants
import pogolitics.view.pagePath
import react.Props
import react.dom.a
import react.fc
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledSpan

val Breadcrumbs = fc<BreadcrumbsProps> { props ->
    props.page?.let { thePage ->
        styledDiv {
            css { +BreadcrumbsStyles.breadcrumbsWrapper }
            thePage.getFullPath().forEachIndexed { index, page ->
                if (index > 0) {
                    styledSpan {
                        css { +BreadcrumbsStyles.separator }
                        +"»"
                    }
                }
                styledSpan {
                    a(href = pagePath(page)) {
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

private object BreadcrumbsStyles: StyleSheet("BreadcrumbsComponentStyles", isStatic = true) {
    val breadcrumbsWrapper by css {
        borderBottom = "1px ${StyleConstants.Colors.primary.bg} solid"
        paddingLeft = StyleConstants.Padding.medium
    }

    val separator by css {
        marginLeft = StyleConstants.Padding.small
        marginRight = StyleConstants.Padding.small
    }
}