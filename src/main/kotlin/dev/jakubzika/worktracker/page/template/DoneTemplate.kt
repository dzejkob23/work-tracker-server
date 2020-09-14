package dev.jakubzika.worktracker.page.template

import dev.jakubzika.worktracker.routing.Endpoint
import io.ktor.html.*
import kotlinx.html.*

class DoneTemplate(
        private val main: MainTemplate = MainTemplate(),
        private val onContinueRoute: Endpoint,
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
                            action = onContinueRoute.url,
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