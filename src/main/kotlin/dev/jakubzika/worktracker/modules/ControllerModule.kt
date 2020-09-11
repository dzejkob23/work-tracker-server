package dev.jakubzika.worktracker.modules

import dev.jakubzika.worktracker.controler.RegistrationController
import dev.jakubzika.worktracker.controler.RegistrationControllerImpl
import org.koin.dsl.module

val controllerModule = module {

    single<RegistrationController> { RegistrationControllerImpl(get()) }

}