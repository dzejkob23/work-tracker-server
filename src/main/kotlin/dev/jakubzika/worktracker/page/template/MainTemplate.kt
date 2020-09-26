package dev.jakubzika.worktracker.page.template

import dev.jakubzika.worktracker.routing.PAGE_TITLE
import io.ktor.html.*
import kotlinx.html.*

class MainTemplate : Template<HTML> {

    val content = Placeholder<HtmlBlockTag>()

    override fun HTML.apply() {
        head {
            title(PAGE_TITLE)
        }
        body {
            insert(content)
        }
    }
}