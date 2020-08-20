package dev.jakubzika.worktracker.repository

import dev.jakubzika.worktracker.db.Schema.WorkSession
import dev.jakubzika.worktracker.db.Schema.WorkSessions
import dev.jakubzika.worktracker.extensions.dbQuery
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement

interface WorkSessionRepository {
    suspend fun addWorkSession(
            startTime: String,
            endTime: String,
            userId: Int,
            projectId: Int
    ): WorkSession?
    suspend fun findWorkSession(workSessionId: Int): WorkSession?
    suspend fun finUsersWorkSessions(userId: Int): List<WorkSession>
    suspend fun deleteWorkSession(workSessionId: Int)
    suspend fun deleteUsersWorkSessions(userId: Int)
}

class WorkSessionRepositoryImpl : WorkSessionRepository {

    override suspend fun addWorkSession(startTime: String, endTime: String, userId: Int, projectId: Int): WorkSession? {
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

    override suspend fun findWorkSession(workSessionId: Int): WorkSession? = dbQuery {
        WorkSessions.select { WorkSessions.id.eq(workSessionId) }.map { WorkSessions.toWorkSession(it) }.singleOrNull()
    }

    override suspend fun finUsersWorkSessions(userId: Int): List<WorkSession> = dbQuery {
        WorkSessions.select { WorkSessions.userId.eq(userId) }.map { WorkSessions.toWorkSession(it) }
    }

    override suspend fun deleteWorkSession(workSessionId: Int) {
        dbQuery {
            WorkSessions.deleteWhere { WorkSessions.id.eq(workSessionId) }
        }
    }

    override suspend fun deleteUsersWorkSessions(userId: Int) {
        dbQuery {
            WorkSessions.deleteWhere { WorkSessions.userId.eq(userId) }
        }
    }
}