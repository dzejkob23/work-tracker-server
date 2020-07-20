package dev.jakubzika.worktracker.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        Database.connect(hikari())

        transaction {
            SchemaUtils.create(Schema.Users)
            SchemaUtils.create(Schema.Projects)
        }
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.jdbcUrl = System.getenv("JDBC_DATABASE_URL")
        config.username = System.getenv("DB_USER")
        config.password = System.getenv("DB_PASSWORD")
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