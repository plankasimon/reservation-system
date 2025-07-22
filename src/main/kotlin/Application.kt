package cz.monetplus

import cz.monetplus.rest.configureReservationRouting
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureHTTP()
    configureMonitoring()
    configureSecurity()
    configureRouting()
    configureSerialization()
    configureDatabases()
    configureReservationRouting()
}
