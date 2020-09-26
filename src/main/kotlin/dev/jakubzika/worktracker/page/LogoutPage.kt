package dev.jakubzika.worktracker.page

import dev.jakubzika.worktracker.auth.logoutUser
import dev.jakubzika.worktracker.routing.Endpoint
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Routing.logoutPage(endpoint: Endpoint = Endpoint.LOGOUT) {
    get(endpoint.url) {
        // do logout
        logoutUser()
        // redirect to HOME page
        call.respondRedirect(Endpoint.HOME.url)
    }
}