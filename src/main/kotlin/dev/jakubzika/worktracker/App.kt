package dev.jakubzika.worktracker

import dev.jakubzika.worktracker.auth.SESSION_LOGIN_NAME
import dev.jakubzika.worktracker.auth.SessionLogin
import dev.jakubzika.worktracker.db.DatabaseClient
import dev.jakubzika.worktracker.module.appModule
import dev.jakubzika.worktracker.module.controllerModule
import dev.jakubzika.worktracker.module.repositoryModule
import dev.jakubzika.worktracker.routing.api
import dev.jakubzika.worktracker.routing.web
import dev.jakubzika.worktracker.utils.isProd
import io.ktor.application.*
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

const val APP_NAME = "Work-Tracker"

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
    dbClient.init(isProd)

    install(DefaultHeaders)
    install(CallLogging) {
        level = Level.DEBUG
    }

    install(StatusPages) {
        // todo - add status pages
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
    }

    install(Sessions) {
        cookie<SessionLogin>(SESSION_LOGIN_NAME)
    }

    install(ContentNegotiation) { gson { } }

    install(Routing) {
        api()
        web()
    }
}