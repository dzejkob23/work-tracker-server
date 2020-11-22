package dev.jakubzika.worktracker.page

import dev.jakubzika.worktracker.auth.isUserAuthorized
import dev.jakubzika.worktracker.extension.translationId
import dev.jakubzika.worktracker.page.section.ProfileSection
import dev.jakubzika.worktracker.page.section.StatisticSection
import dev.jakubzika.worktracker.page.section.TrackingSectionTemplate
import dev.jakubzika.worktracker.page.template.MainTemplate
import dev.jakubzika.worktracker.routing.Endpoint
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.routing.*
import kotlinx.html.*

fun Routing.homePage(endpoint: Endpoint = Endpoint.HOME) {
    get(endpoint.url) {
        call.respondHtmlTemplate(
                if (isUserAuthorized()) {
                    mainView()
                } else {
                    unauthorizedView()
                }
        ) {}
    }
}

private fun unauthorizedView(main: MainTemplate = MainTemplate()) = object : Template<HTML> {
    override fun HTML.apply() {
        insert(main) {
            content {
                div(classes = "flex-vertical") {
                    h1 { translationId("home-page-title") }
                    p { translationId("home-page-subtitle") }
                    form(
                            action = Endpoint.REGISTRATION.url,
                            encType = FormEncType.textPlain,
                            method = FormMethod.get
                    ) {
                        submitInput(classes = "buttonPrimary") {
                            translationId("home-form-button-register")
                        }
                    }
                    form(
                            action = Endpoint.LOGIN.url,
                            encType = FormEncType.textPlain,
                            method = FormMethod.get
                    ) {
                        submitInput(classes = "buttonPrimary") {
                            translationId("home-form-button-login")
                        }
                    }
                }
            }
        }
    }
}

private fun mainView(main: MainTemplate = MainTemplate()) = object : Template<HTML> {
    override fun HTML.apply() {
        insert(main) {
            headers {
                script { src = "/static/js/utils.js" }
            }
            content {
                div(classes = "flex-horizontal") {
                    insert(TrackingSectionTemplate()) {}
                    insert(ProfileSection()) {}
                    insert(StatisticSection()) {}
                }
            }
        }
    }
}