package dev.jakubzika.worktracker.repository

import dev.jakubzika.worktracker.db.Schema.WorkSession
import dev.jakubzika.worktracker.db.Schema.WorkSessions
import dev.jakubzika.worktracker.extension.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement
import java.time.LocalDateTime

interface WorkSessionRepository {
    suspend fun addWorkSession(
            startTime: String,
            endTime: String,
            userId: Long,
            projectId: Long
    ): WorkSession?
    suspend fun findWorkSession(workSessionId: Long): WorkSession?
    suspend fun findUsersWorkSessions(userId: Long): List<WorkSession>
    suspend fun deleteWorkSession(workSessionId: Long)
    suspend fun deleteUsersWorkSessions(userId: Long)
    suspend fun endWorkSession(sessionId: Long)
}

class WorkSessionRepositoryImpl : WorkSessionRepository {

    override suspend fun addWorkSession(startTime: String, endTime: String, userId: Long, projectId: Long): WorkSession? {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement = WorkSessions.insert { workSession ->
                workSession[WorkSessions.startTime] = startTime
                workSession[WorkSessions.endTime] = endTime
                workSession[WorkSessions.userId] = userId
                workSession[WorkSessions.projectId] = projectId
            }
        }
        return WorkSessions.toWorkSession(statement?.resultedValues?.get(0) ?: return null)
    }

    override suspend fun findWorkSession(workSessionId: Long): WorkSession? = dbQuery {
        WorkSessions.select { WorkSessions.id.eq(workSessionId) }.map { WorkSessions.toWorkSession(it) }.singleOrNull()
    }

    override suspend fun findUsersWorkSessions(userId: Long): List<WorkSession> = dbQuery {
        WorkSessions.select { WorkSessions.userId.eq(userId) }.map { WorkSessions.toWorkSession(it) }
    }

    override suspend fun deleteWorkSession(workSessionId: Long) {
        dbQuery {
            WorkSessions.deleteWhere { WorkSessions.id.eq(workSessionId) }
        }
    }

    override suspend fun deleteUsersWorkSessions(userId: Long) {
        dbQuery {
            WorkSessions.deleteWhere { WorkSessions.userId.eq(userId) }
        }
    }

    override suspend fun endWorkSession(sessionId: Long) {
        dbQuery {
            WorkSessions.update({ WorkSessions.id.eq(sessionId) }) {
                it[endTime] = LocalDateTime.now().toString()
            }
        }
    }
}