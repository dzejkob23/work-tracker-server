package dev.jakubzika.worktracker.page.section

import dev.jakubzika.worktracker.routing.Endpoint
import io.ktor.html.*
import kotlinx.html.*

class ProfileSection : Template<FlowContent> {
    override fun FlowContent.apply() {
        section(classes = "card") {
            h2 { +"Profil" }
            p { +"Jakub Zíka" }
            p { +"Nastavení" }
            p {
                form(
                        action = Endpoint.LOGOUT.url,
                        encType = FormEncType.textPlain,
                        method = FormMethod.get
                ) {
                    submitInput(classes = "buttonPrimary") { value = "Logout" }
                }
            }
        }
    }
}