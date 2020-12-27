package dev.jakubzika.worktracker.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseClient {

    fun init() {
        Database.connect(hikari())

        transaction {
            SchemaUtils.create(Schema.Users)
            SchemaUtils.create(Schema.Projects)
            SchemaUtils.create(Schema.WorkSessions)
        }
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.jdbcUrl = "jdbc:${System.getenv("DATABASE_URL")}"
        config.username = System.getenv("DATABASE_USER")
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"

        config.addDataSourceProperty("cachePrepStmts", "true")
        config.addDataSourceProperty("prepStmtCacheSize", "250")
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")

        config.validate()

        return HikariDataSource(config)
    }
}