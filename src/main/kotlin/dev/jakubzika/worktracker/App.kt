package dev.jakubzika.worktracker

import dev.jakubzika.worktracker.auth.MySession
import dev.jakubzika.worktracker.auth.SESSION_NAME
import dev.jakubzika.worktracker.controler.LoginController
import dev.jakubzika.worktracker.db.DatabaseClient
import dev.jakubzika.worktracker.module.appModule
import dev.jakubzika.worktracker.module.controllerModule
import dev.jakubzika.worktracker.module.repositoryModule
import dev.jakubzika.worktracker.routing.api
import dev.jakubzika.worktracker.routing.web
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.content.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.util.*
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import org.slf4j.event.Level
import kotlin.collections.set

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

const val AUTH_USER = "userAuth"

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalAPI
@Suppress("unused") // Referenced in application.conf
fun Application.main() {

    install(Koin) {
        modules(
                appModule,
                repositoryModule,
                controllerModule
        )
    }

    val dbClient: DatabaseClient by inject()
    dbClient.init()

    install(DefaultHeaders)
    install(CallLogging) {
        level = Level.DEBUG
    }

    install(StatusPages) {
        // fixme - it must be finalized to custom usability
        // it is possible catch exception and respond text
        // then must be these exception paired to custom StatusCodePages repsonses
        exception<NotFoundException> { e ->
            call.respondText(
                    e.localizedMessage,
                    ContentType.Text.Plain,
                    HttpStatusCode.InternalServerError
            )
        }
        exception<IllegalArgumentException> {
            call.respond(HttpStatusCode.BadRequest)
        }

        // show error html
        // status code replace # in "error#.html"
        // so there will be codes redirected on pages error404.html and error401.html
        statusFile(HttpStatusCode.NotFound, HttpStatusCode.Unauthorized, filePattern = "error#.html")

        // or catch status code alone and do own action
        status(HttpStatusCode.InternalServerError) {
            call.respond(TextContent("${it.value} ${it.description}", ContentType.Text.Plain.withCharset(Charsets.UTF_8), it))
        }

        // more about StatusPages: https://ktor.io/docs/status-pages.html#exceptions
    }

    // todo - zapojit do akce
    install(Sessions) {
        cookie<MySession>(SESSION_NAME) {
            cookie.extensions["SameSite"] = "lax"
        }
    }

    install(ContentNegotiation) { gson { } }

    install(Authentication) {
        basic(AUTH_USER) {
            realm = "Basic auth form"
            validate { credentials ->
                val loginController: LoginController by inject()
                loginController.authenticate(credentials.name, credentials.password)
            }
        }
    }

    install(Routing) {
        api()
        web()
    }
}