package dev.jakubzika.worktracker

import dev.jakubzika.worktracker.auth.JwtService
import dev.jakubzika.worktracker.auth.MySession
import dev.jakubzika.worktracker.auth.hash
import dev.jakubzika.worktracker.db.DatabaseFactory
import dev.jakubzika.worktracker.repository.UserRepositoryImpl
import dev.jakubzika.worktracker.routing.api
import dev.jakubzika.worktracker.routing.web
import dev.jakubzika.worktracker.utils.getEnvironmentProperty
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.routing.Routing
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

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalAPI
@Suppress("unused") // Referenced in application.conf
fun Application.main() {

    install(CallLogging)

    install(Sessions) {
        cookie<MySession>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }

    // init database
    DatabaseFactory.init()

    val db = UserRepositoryImpl()

    val jwtService = JwtService()
    val hashFunction = { s: String -> hash(s) }

    install(Authentication) {

        val jwtIssuer = getEnvironmentProperty("ktor.jwt.domain")
        val jwtAudience = getEnvironmentProperty("ktor.jwt.audience")
        val jwtRealm = getEnvironmentProperty("ktor.jwt.realm")

        jwt {
            realm = jwtRealm
            verifier(jwtService.makeJwtVerifier(jwtIssuer, jwtAudience))
            validate { credential ->

                val payload = credential.payload
                val claim = payload.getClaim("id")
                val claimString = claim.asInt()
                val user = db.findUser(claimString)

                if (user != null) {
                    JWTPrincipal(credential.payload)
                } else null

//                if (credential.payload.audience.contains(jwtAudience)) {
//                    JWTPrincipal(credential.payload)
//                } else null
            }
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