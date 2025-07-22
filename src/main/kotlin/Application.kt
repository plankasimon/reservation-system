import io.ktor.server.application.*
import io.ktor.server.netty.*
import routing.configureReservationRouting

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
