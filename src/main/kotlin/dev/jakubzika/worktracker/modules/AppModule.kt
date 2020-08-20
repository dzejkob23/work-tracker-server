package dev.jakubzika.worktracker.modules

import dev.jakubzika.worktracker.db.DatabaseClient
import org.koin.dsl.module

val appModule = module {

    single { DatabaseClient() }

}