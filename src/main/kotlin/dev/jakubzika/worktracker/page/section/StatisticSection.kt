package dev.jakubzika.worktracker.page.section

import io.ktor.html.*
import kotlinx.html.FlowContent
import kotlinx.html.h2
import kotlinx.html.h3
import kotlinx.html.section

class StatisticSection : Template<FlowContent> {
    override fun FlowContent.apply() {
        section(classes = "card") {
            h2 { +"Statistiky" }
            h3 { +"Denní" }
            h3 { +"Týdenní" }
            h3 { +"Měsíční" }
            h3 { +"Roční" }
        }
    }
}