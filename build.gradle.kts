plugins {
    application
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
}

version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
    java {
        toolchain { languageVersion.set(JavaLanguageVersion.of(21))}
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.swagger)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.negotiation)
    implementation(libs.ktor.json.serialization)
    implementation(libs.ktor.server.config.yaml)
    testImplementation(libs.ktor.server.test.host)

    implementation(libs.logback.classic)

    testImplementation(libs.kotlin.test.junit)

    implementation(libs.kotlin.logging)

    implementation(libs.postgres)

    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.java.time)
    implementation(libs.exposed.kotlin.datetime)
    implementation(libs.exposed.r2dbc)
    implementation(libs.exposed.dao)
}
