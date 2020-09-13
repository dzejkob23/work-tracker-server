package dev.jakubzika.worktracker.module

import dev.jakubzika.worktracker.repository.*
import org.koin.dsl.module

val repositoryModule = module {

    single<ProjectRepository> { ProjectRepositoryImpl() }

    single<UserRepository> { UserRepositoryImpl() }

    single<WorkSessionRepository> { WorkSessionRepositoryImpl() }

}