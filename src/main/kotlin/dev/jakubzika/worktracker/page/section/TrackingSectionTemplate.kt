package dev.jakubzika.worktracker.page.section

import dev.jakubzika.worktracker.controler.TrackingController
import io.ktor.html.*
import kotlinx.html.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class TrackingSectionTemplate : KoinComponent, Template<FlowContent> {

    val controller: TrackingController by inject()

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