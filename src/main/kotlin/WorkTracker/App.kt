package WorkTracker

import io.ktor.jackson.*
import io.ktor.features.ContentNegotiation
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.request.receive
import io.ktor.server.netty.Netty
import io.ktor.server.engine.embeddedServer

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

import org.apache.log4j.PropertyConfigurator
import org.apache.log4j.BasicConfigurator

import WorkTracker.db.Schema
import WorkTracker.db.Schema.User
import WorkTracker.db.Schema.Users

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
        watchPaths = listOf("WorkTracker"),
        module = Application::module,
        port = 8080
    ).start(wait = true)
}

fun Application.module() {
    initLogging()
    initDB()
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

private fun initDB() {
    val config = HikariConfig()
    config.setJdbcUrl("jdbc:postgresql://localhost:5432/work-tracker")
    config.setUsername("zikaj")
    config.setPassword("test")
    config.addDataSourceProperty("cachePrepStmts", "true")
    config.addDataSourceProperty("prepStmtCacheSize", "250")
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
    val ds = HikariDataSource(config)
    
    Database.connect(ds)
    Schema.create()
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