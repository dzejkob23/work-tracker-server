package dev.jakubzika.worktracker.routing

import dev.jakubzika.worktracker.db.Schema
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

const val API_VERSION_V1 = "/v1"

const val USER = "$API_VERSION_V1/user"
const val USER_LOGIN = "$USER/login"
const val USER_CREATE = "$USER/create"
const val USER_FIND = "$USER/find"

const val PROJECT = "$API_VERSION_V1/project"

fun Routing.api() {
    users()
    projects()
}

fun Routing.users() {
    route(USER) {
        get {
            val users = transaction {
                Schema.Users.selectAll().map {
                    Schema.Users.toUser(it)
                }
            }
            call.respond(users)
        }
        post {
            val user = call.receive<Schema.User>()
            call.respond(user)
        }
    }
    route(USER_CREATE) {
        // todo
    }
    route(USER_LOGIN) {
        // todo
    }
    route(USER_FIND) {
        // todo
    }
}

fun Routing.projects() {
    route(PROJECT) {
        get {
            call.respondText("API v1 project", contentType = ContentType.Text.Plain)
        }
    }
}