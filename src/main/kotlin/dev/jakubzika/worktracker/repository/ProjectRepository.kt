package dev.jakubzika.worktracker.repository

import dev.jakubzika.worktracker.db.Schema.Project
import dev.jakubzika.worktracker.db.Schema.Projects
import dev.jakubzika.worktracker.extension.dbQuery
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement

interface ProjectRepository {
    suspend fun addProject(
            name: String,
            startTime: String,
            endTime: String,
            userId: Int
    ): Project?
    suspend fun findProject(projectId: Int): Project?
    suspend fun findUsersProjects(userId: Int): List<Project>
    suspend fun deleteProject(projectId: Int)
    suspend fun deleteUsersProjects(userId: Int)
}

class ProjectRepositoryImpl : ProjectRepository {

    override suspend fun addProject(
            name: String,
            startTime: String,
            endTime: String,
            userId: Int
    ): Project? {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement = Projects.insert { project ->
                project[Projects.name] = name
                project[Projects.startTime] = startTime
                project[Projects.endTime] = endTime
                project[Projects.userId] = userId
            }
        }
        return Projects.toProject(statement?.resultedValues?.get(0) ?: return null)
    }

    override suspend fun findProject(projectId: Int): Project? = dbQuery {
        Projects.select { Projects.id.eq(projectId) }.map { Projects.toProject(it) }.singleOrNull()
    }

    override suspend fun findUsersProjects(userId: Int): List<Project> {
        return Projects.select { Projects.userId.eq(userId) }.map { Projects.toProject(it) }
    }

    override suspend fun deleteProject(projectId: Int) {
        dbQuery {
            Projects.deleteWhere { Projects.id.eq(projectId) }
        }
    }

    override suspend fun deleteUsersProjects(userId: Int) {
        dbQuery {
            Projects.deleteWhere { Projects.userId.eq(userId) }
        }
    }
}