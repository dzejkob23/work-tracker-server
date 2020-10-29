val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project
val postgres_version: String by project
val cassandra_version: String by project
val hikari_version: String by project
val koin_version: String by project

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.72"
    application
}

group = "dev.jakubzika.worktracker"
version = "0.0.1"

application {
    mainClassName = "$group.AppKt"
}

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}

dependencies {

    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    // Ktor
    implementation("io.ktor", "ktor-server-core", ktor_version)
    implementation("io.ktor", "ktor-server-netty", ktor_version)
    implementation("io.ktor", "ktor-gson", ktor_version)
    implementation("io.ktor", "ktor-auth-jwt", ktor_version)
    implementation("io.ktor", "ktor-html-builder", ktor_version)

    // Exposed (DB)
    implementation("org.jetbrains.exposed", "exposed-core", exposed_version)
    implementation("org.jetbrains.exposed", "exposed-jdbc", exposed_version)

    // PostgreSql + DB configuration
    implementation("org.postgresql", "postgresql", postgres_version)
    implementation("org.apache.cassandra", "cassandra-all", cassandra_version)
    implementation("com.zaxxer", "HikariCP", hikari_version)

    // Logging
    implementation("ch.qos.logback", "logback-classic", logback_version)

    // Koin
    implementation("org.koin", "koin-core", koin_version)
    implementation("org.koin", "koin-ktor", koin_version)
    implementation("org.koin", "koin-logger-slf4j", koin_version)

}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    kotlinOptions.freeCompilerArgs += "-Xopt-in=org.mylibrary.OptInAnnotation"
}