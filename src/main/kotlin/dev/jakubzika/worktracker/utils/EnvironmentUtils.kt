package dev.jakubzika.worktracker.utils

import io.ktor.application.Application
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
fun Application.getEnvironmentProperty(name: String): String = environment.config.property(name).getString()

@KtorExperimentalAPI
val Application.envKind get() = getEnvironmentProperty("ktor.environment")

@KtorExperimentalAPI
val Application.isDev get() = envKind == "dev"

@KtorExperimentalAPI
val Application.isProd get() = envKind == "prod"