package dev.jakubzika.worktracker.module

import dev.jakubzika.worktracker.controler.LoginController
import dev.jakubzika.worktracker.controler.LoginControllerImpl
import dev.jakubzika.worktracker.controler.RegistrationController
import dev.jakubzika.worktracker.controler.RegistrationControllerImpl
import org.koin.dsl.module

val controllerModule = module {

    single<RegistrationController> { RegistrationControllerImpl(get()) }

    single<LoginController> { LoginControllerImpl(get()) }

}