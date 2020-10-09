package dev.jakubzika.worktracker.page.template

import dev.jakubzika.worktracker.APP_NAME
import dev.jakubzika.worktracker.routing.PAGE_TITLE
import io.ktor.html.*
import kotlinx.html.*

class MainTemplate : Template<HTML> {

    val headers = Placeholder<HtmlHeadTag>()
    val content = Placeholder<HtmlBlockTag>()

    override fun HTML.apply() {
        head {
            title(PAGE_TITLE)
            link(href = "/static/css/style.css", rel = "stylesheet")
            insert(headers)
        }
        body {
            header {
                h1 { +APP_NAME }
            }
            main {
                insert(content)
            }
        }
    }
}