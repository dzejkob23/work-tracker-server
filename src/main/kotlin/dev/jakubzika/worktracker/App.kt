package dev.jakubzika.worktracker

import io.ktor.jackson.*
import io.ktor.features.ContentNegotiation
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.request.receive
import io.ktor.server.netty.Netty
import io.ktor.server.engine.embeddedServer

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.jakubzika.worktracker.db.DatabaseFactory

import org.apache.log4j.BasicConfigurator

import dev.jakubzika.worktracker.db.Schema
import dev.jakubzika.worktracker.db.Schema.User
import dev.jakubzika.worktracker.db.Schema.Users
import io.ktor.http.ContentType
import io.ktor.server.engine.applicationEngineEnvironment

/* 
 * ## Run auto-reloading ##
 * terminal_1: gradle -t installDist
 * terminal_2: gradle run
 * 
 * ## Send Json data on server ##
 * curl -H "Content-Type: application/json" -d '{"name":"John","age":42}' http://localhost:8080
 * 
 */

fun main() {
    embeddedServer(
        Netty,
        watchPaths = listOf("dev/jakubzika/worktracker"),
        module = Application::module,
        port = 8080
    ).start(wait = true)
}

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@SuppressWarnings("unused") // Referenced in application.conf
fun Application.main() {
    initLogging()
    DatabaseFactory.init()
    contentNegotiation()
    database()
}

private fun Application.contentNegotiation() {
    install(ContentNegotiation) {
        jackson {
            // Configre Jaskcon's ObjectMapper here
        }
    }
}

private fun initLogging() {
    BasicConfigurator.configure()
    // PropertyConfigurator.configure("log4j.properties")
}

private fun Application.database() {

    install(Routing) { 
        route("/user") {
            get("/") {
                val users = transaction {
                    Users.selectAll().map {
                        Users.toUser(it)
                    }
                }
                call.respond(users)
            }
            post("/") {
                val user = call.receive<User>()
                call.respond(user)
            }
        }
    }
}

const val API_VERSION = "/v1"