package dev.jakubzika.worktracker.routing

import dev.jakubzika.worktracker.APP_NAME
import dev.jakubzika.worktracker.page.homePage
import dev.jakubzika.worktracker.page.loginPage
import dev.jakubzika.worktracker.page.logoutPage
import dev.jakubzika.worktracker.page.registrationPage
import io.ktor.routing.*

// Web common constants
const val PAGE_TITLE = APP_NAME

// Form common attributes keys
const val FORM_FIELD_NAME = "username"
const val FORM_FIELD_PASSWD = "password"
const val FORM_FIELD_PASSWD_AGAIN = "password_again"

fun Routing.web() {

    homePage()
    registrationPage()
    loginPage()
    logoutPage()

}