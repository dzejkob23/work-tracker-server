package dev.jakubzika.worktracker.module

import dev.jakubzika.worktracker.db.DatabaseClient
import org.koin.dsl.module

val appModule = module {

    single { DatabaseClient() }

}