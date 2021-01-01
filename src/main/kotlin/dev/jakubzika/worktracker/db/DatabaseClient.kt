package dev.jakubzika.worktracker.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.net.URI

class DatabaseClient {

    fun init(isProd: Boolean) {
        Database.connect(hikari(isProd))

        transaction {
            SchemaUtils.create(Schema.Users)
            SchemaUtils.create(Schema.Projects)
            SchemaUtils.create(Schema.WorkSessions)
        }
    }

    private fun hikari(isProd: Boolean): HikariDataSource {
        val config = HikariConfig()
        val dbUri = URI(System.getenv("DATABASE_URL"))
        val sslMode = if (isProd) "?sslmode=require" else ""

        config.jdbcUrl = "jdbc:${dbUri.scheme}://${dbUri.host}:${dbUri.port}${dbUri.path}${sslMode}"
        config.username = dbUri.userInfo.split(':')[0]
        config.password = dbUri.userInfo.split(':')[1]
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