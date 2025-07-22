package cz.monetplus.rest

import cz.monetplus.requests.ReservationRequest
import cz.monetplus.service.ReservationService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
 
fun Application.configureReservationRouting() {
    val reservationService = ReservationService()

    routing {
        get("/reservations") {
            call.respond(reservationService.getAllReservations())
        }

        post("/reserve-table") {
            val reservationRequest = call.receive<ReservationRequest>()
            val result = reservationService.reserveTable(reservationRequest)
            if (result.first) {
                call.respond(HttpStatusCode.Created, "Table reserved")
            } else {
                call.respond(HttpStatusCode.BadRequest, result.second)
            }
        }

        delete("/delete-reservation/{id}") {
            val id = call.parameters["id"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            reservationService.deleteReservationById(id.toInt())
            call.respond(HttpStatusCode.OK)
        }
    }
}