package dev.jakubzika.worktracker.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Date
import java.sql.Timestamp

object Schema {

    /** Tables */
    object Users : Table() {
        val id: Column<Int> = integer("id").autoIncrement()
        val nickname: Column<String> = varchar("nickname", 255)
        val passwd: Column<String> = varchar("passwd", 255)
    
        override val primaryKey = PrimaryKey(id, name="PK_User_ID")

        fun toUser(row: ResultRow): User = User(
                id = row[id],
                nickname = row[nickname],
                passwd = row[passwd]
        )
    }
    data class User(
        val id: Int? = null, 
        val nickname: String,
        val passwd: String
    )

    object Projects : Table() {
        val id: Column<Int> = integer("id").autoIncrement()
        val name: Column<String> = varchar("name", 255)
        val startTime: Column<String> = varchar("startTime", 255)
        val endTime: Column<String> = varchar("endTime", 255)
        val userId: Column<Int> = integer("userId").references(Users.id)

        override val primaryKey = PrimaryKey(Users.id, name="PK_Project_ID")

        fun toProject(row: ResultRow): Project = Project(
                id = row[id],
                name = row[name],
                start = row[startTime],
                end = row[endTime],
                userId = row[userId]
        )
    }
    data class Project(
        val id: Int? = null,
        val name: String,
        val start: String,
        val end: String,
        val userId: Int
    )

    /** Operations */
    fun Transaction.insertUser(nickname: String, passwd: String) {
        Users.insert {
            it[Users.nickname] = nickname
            it[Users.passwd] = passwd
        }
    }

    fun insertUser(nickname: String, passwd: String) {
        transaction {
            Users.insert {
                it[Users.nickname] = nickname
                it[Users.passwd] = passwd
            }
        }
    }
}

suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }
