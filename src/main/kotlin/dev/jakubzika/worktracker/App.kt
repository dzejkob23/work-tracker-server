package dev.jakubzika.worktracker

import dev.jakubzika.worktracker.auth.JwtService
import dev.jakubzika.worktracker.auth.MySession
import dev.jakubzika.worktracker.auth.hash
import dev.jakubzika.worktracker.db.DatabaseFactory
import dev.jakubzika.worktracker.repository.UserRepositoryImpl
import dev.jakubzika.worktracker.routing.api
import dev.jakubzika.worktracker.routing.web
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.Credential
import io.ktor.auth.Principal
import io.ktor.auth.jwt.jwt
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.routing.Routing
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie

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

const val API_VERSION = "/v1"

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.main(testing: Boolean = false) {

    install(Sessions) {
        cookie<MySession>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }

    // init database
    DatabaseFactory.init()
    contentNegotiation()
    database()
}

    }

    // install JSON file processor
    install(ContentNegotiation) {
        gson { /* Configre Jaskcon's ObjectMapper here */ }
    }

    // install calling routes
    install(Routing) {
        web()
        api()
    }
}