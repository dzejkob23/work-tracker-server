package dev.jakubzika.worktracker

import dev.jakubzika.worktracker.auth.MySession
import dev.jakubzika.worktracker.routing.LOGIN
import dev.jakubzika.worktracker.routing.api
import dev.jakubzika.worktracker.routing.web
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.authenticate
import io.ktor.auth.basic
import io.ktor.features.*
import io.ktor.gson.gson
import io.ktor.http.*
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import io.ktor.util.KtorExperimentalAPI

/* 
 * ## Run auto-reloading ##
 * terminal_1: gradle -t installDist
 * terminal_2: gradle run
 * 
 * ## Send Json data on server ##
 * curl -H "Content-Type: application/json" -d '{"name":"John","age":42}' http://localhost:8080
 *
 * ## Run server
 * gradle clean build run
 */

const val APP_NAME = "WorkTracker"

const val AUTH_BASIC = "auth"

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalAPI
@Suppress("unused") // Referenced in application.conf
fun Application.main() {

    // todo - spojit s loginem
    //DatabaseFactory.init()

    install(DefaultHeaders)
    install(CallLogging)

    install(StatusPages) {
        exception<Throwable> { e ->
            call.respondText(
                    e.localizedMessage,
                    ContentType.Text.Plain,
                    HttpStatusCode.InternalServerError
            )
        }
    }

    // todo - zapojit do akce
    install(Sessions) {
        cookie<MySession>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }

    install(ContentNegotiation) {
        gson { /* Configre Jaskcon's ObjectMapper here */ }
    }

    install(Authentication) {
        basic(AUTH_BASIC) {
            realm = "Basic auth form"
            validate { credentials ->
                if (credentials.name == credentials.password) {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
    }

    install(Routing) {
        authenticate(AUTH_BASIC) {
            get(LOGIN) {
                call.respondText("Access secure area")
            }
        }
        api()
        web()
    }
}