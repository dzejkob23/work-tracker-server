package dev.jakubzika.worktracker.routing

import dev.jakubzika.worktracker.APP_NAME
import dev.jakubzika.worktracker.AUTH_USER
import dev.jakubzika.worktracker.pages.homePage
import dev.jakubzika.worktracker.pages.registrationPage
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.routing.*

// Web common constants
const val PAGE_TITLE = APP_NAME

// Form common attributes keys
const val FORM_FIELD_NAME = "username"
const val FORM_FIELD_PASSWD = "password"
const val FORM_FIELD_PASSWD_AGAIN = "password_again"

fun Routing.web() {

    homePage(Endpoint.HOME.url)
    registrationPage(Endpoint.REGISTRATION.url)

    authenticate(AUTH_USER) {
        get(Endpoint.LOGIN.url) {
            val user = call.authentication.principal
            call.respondText("Access secure area: $user")
        }
    }
}