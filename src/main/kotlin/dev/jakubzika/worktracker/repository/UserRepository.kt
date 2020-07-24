package dev.jakubzika.worktracker.repository

import dev.jakubzika.worktracker.db.Schema.User
import dev.jakubzika.worktracker.db.Schema.Users
import dev.jakubzika.worktracker.extension.dbQuery
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement

interface UserRepository {
    suspend fun findUser(userId: Int): User?
    suspend fun addUser(nickname: String, password: String): User?
}

class UserRepositoryImpl : UserRepository {
    override suspend fun findUser(userId: Int): User? = dbQuery {
        Users.select { Users.id.eq(userId) }.map { Users.toUser(it) }.singleOrNull()
    }

    override suspend fun addUser(nickname: String, password: String): User? {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement = Users.insert { user ->
                user[Users.nickname] = nickname
                user[Users.password] = password
            }
        }
        return Users.toUser(statement?.resultedValues?.get(0) ?: return null)
    }
}