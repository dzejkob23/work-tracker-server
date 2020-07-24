package dev.jakubzika.worktracker.utils

import io.ktor.application.Application
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
private fun Application.getEnvKind(): String {
    return environment.config.property("ktor.environment").getString()
}

fun Application.isDev(): Boolean = getEnvKind() == "dev"
fun Application.isProd(): Boolean = !isDev()