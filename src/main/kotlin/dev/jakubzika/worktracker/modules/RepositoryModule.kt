package dev.jakubzika.worktracker.modules

import dev.jakubzika.worktracker.repository.*
import org.koin.dsl.module

val repositoryModule = module {

    single<ProjectRepository> { ProjectRepositoryImpl() }

    single<UserRepository> { UserRepositoryImpl() }

    single<WorkSessionRepository> { WorkSessionRepositoryImpl() }

}