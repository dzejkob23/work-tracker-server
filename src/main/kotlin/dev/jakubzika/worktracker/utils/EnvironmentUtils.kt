package dev.jakubzika.worktracker.utils

import io.ktor.application.Application
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
fun Application.getEnvironmentProperty(name: String): String = environment.config.property(name).getString()

@KtorExperimentalAPI
private fun Application.getEnvKind(): String = getEnvironmentProperty("ktor.environment")

@KtorExperimentalAPI
fun Application.isDev(): Boolean = getEnvKind() == "dev"

@KtorExperimentalAPI
fun Application.isProd(): Boolean = !isDev()