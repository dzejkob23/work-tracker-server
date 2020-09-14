package dev.jakubzika.worktracker.page.template

import dev.jakubzika.worktracker.routing.Endpoint
import io.ktor.html.*
import kotlinx.html.*

class DoneTemplate(
        private val main: MainTemplate = MainTemplate(),
        private val continueRoute: Endpoint,
        private val doneTitle: String

) : Template<HTML> {
    override fun HTML.apply() {
        insert(main) {
            content {
                h2 {
                    +doneTitle
                }
                p {
                    form(
                            action = continueRoute.url,
                            encType = FormEncType.textPlain,
                            method = FormMethod.get
                    ) {
                        submitInput { value = "Continue" }
                    }
                }
            }
        }
    }
}