package dev.jakubzika.worktracker.module

import dev.jakubzika.worktracker.controler.*
import org.koin.dsl.module

val controllerModule = module {

    single<RegistrationController> { RegistrationControllerImpl(get()) }

    single<LoginController> { LoginControllerImpl(get()) }

    single<TrackingController> { TrackingControllerImpl(get()) }

}