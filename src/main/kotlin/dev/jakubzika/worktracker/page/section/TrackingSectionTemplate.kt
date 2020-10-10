package dev.jakubzika.worktracker.page.section

import io.ktor.html.*
import kotlinx.html.*

class TrackingSectionTemplate : Template<FlowContent> {
    override fun FlowContent.apply() {
        section(classes = "card") {
            h2 { +"Měření" }
            p { +"00h 00m 00s" }
            button(classes = "buttonPrimary") { +"Start" }
            button(classes = "buttonPrimary") { +"Stop & Save" }
            button(classes = "buttonPrimary") { +"Restart" }
        }
    }
}