package dev.jakubzika.worktracker.routing

import dev.jakubzika.worktracker.APP_NAME
import dev.jakubzika.worktracker.AUTH_BASIC
import dev.jakubzika.worktracker.pages.homePage
import dev.jakubzika.worktracker.pages.registrationPage
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get


// Web routes
const val HOME = "/"
const val LOGIN = "/login"
const val REGISTRATION = "/registration"

// Web common constants
const val PAGE_TITLE = APP_NAME

// Form common attributes keys
const val FORM_FIELD_NAME = "username"
const val FORM_FIELD_PASSWD = "password"

fun Routing.web() {

    homePage(HOME)
    registrationPage(REGISTRATION)

    authenticate(AUTH_BASIC) {
        get(LOGIN) {
            call.respondText("Access secure area")
        }
    }
}