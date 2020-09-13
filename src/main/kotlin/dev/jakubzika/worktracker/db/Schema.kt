package dev.jakubzika.worktracker.db

import org.jetbrains.exposed.sql.*

object Schema {

    /** USERS */
    object Users : Table() {
        val id: Column<Int> = integer("id").autoIncrement()
        val nickname: Column<String> = varchar("nickname", 255)
        val password: Column<ByteArray> = binary("passwd")
        val salt: Column<ByteArray> = binary("salt")

        override val primaryKey = PrimaryKey(id, name="PK_User_ID")

        fun toUser(row: ResultRow): User = User(
                id = row[id],
                nickname = row[nickname],
                passwd = row[password],
                salt = row[salt]
        )
    }
    data class User(
        val id: Int? = null, 
        val nickname: String,
        val passwd: ByteArray,
        val salt: ByteArray
    )

    /** PROJECTS */
    object Projects : Table() {
        val id: Column<Int> = integer("id").autoIncrement()
        val name: Column<String> = varchar("name", 255)
        val startTime: Column<String> = varchar("startTime", 255)
        val endTime: Column<String> = varchar("endTime", 255)
        val userId: Column<Int> = integer("userId").references(Users.id)

        override val primaryKey = PrimaryKey(id, name="PK_Project_ID")

        fun toProject(row: ResultRow): Project = Project(
                id = row[id],
                name = row[name],
                startTime = row[startTime],
                endTime = row[endTime],
                userId = row[userId]
        )
    }
    data class Project(
        val id: Int? = null,
        val name: String,
        val startTime: String,
        val endTime: String,
        val userId: Int
    )

    /** WORK SESSIONS */
    object WorkSessions : Table() {
        val id: Column<Int> = integer("id").autoIncrement()
        val startTime: Column<String> = varchar("startTime", 255)
        val endTime: Column<String> = varchar("endTime", 255)
        val userId: Column<Int> = integer("userId").references(Users.id)
        val projectId: Column<Int> = integer("projectId").references(Projects.id)

        override val primaryKey = PrimaryKey(id, name="PK_WorkSession_ID")

        fun toWorkSession(row: ResultRow): WorkSession = WorkSession(
                id = row[id],
                startTime = row[startTime],
                endTime = row[endTime],
                userId = row[userId],
                projectId = row[projectId]
        )
    }
    data class WorkSession(
            val id: Int?,
            val startTime: String,
            val endTime: String,
            val userId: Int,
            val projectId: Int
    )
}
