package dev.jakubzika.worktracker.routing

import dev.jakubzika.worktracker.API_VERSION
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

fun Routing.api() {
    route(API_VERSION) {
        route("/user") {
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
        route("/project") {
            get {
                call.respondText("API v1 project", contentType = ContentType.Text.Plain)
            }
        }
    }
}