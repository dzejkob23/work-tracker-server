package dev.jakubzika.worktracker.routing

import dev.jakubzika.worktracker.db.Schema
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun Routing.api() {
    users()
    projects()
}

fun Routing.users() {
    route(Endpoint.USER.url) {
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
    route(Endpoint.USER_CREATE.url) {
        // todo
        handle {
            call.respond(Response(status = "OK"))
        }
    }
    route(Endpoint.USER_LOGIN.url) {
        // todo
        handle {
            call.respond(Response(status = "OK"))
        }
    }
    route(Endpoint.USER_FIND.url) {
        // todo
        handle {
            call.respond(Response(status = "OK"))
        }
    }
}

fun Routing.projects() {
    route(Endpoint.PROJECT.url) {
        get {
            call.respondText("API v1 project", contentType = ContentType.Text.Plain)
        }
    }
}

data class Response(val status: String)