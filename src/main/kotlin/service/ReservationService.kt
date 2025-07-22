package cz.monetplus.service

import cz.monetplus.persistence.entity.ReservationEntity
import cz.monetplus.persistence.service.ReservationDataService
import cz.monetplus.requests.ReservationRequest
import cz.monetplus.requests.toModel
import io.ktor.http.HttpStatusCode
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import mu.KotlinLogging

class ReservationService {
    private val reservationDataService = ReservationDataService()

    suspend fun getAllReservations(): List<ReservationEntity> {
        return reservationDataService.getAllReservations()
    }

    suspend fun reserveTable(request: ReservationRequest): Pair<Boolean, String> {
        val requestedTime = request.date.toJavaLocalDateTime()

        val timeWindow = requestedTime.minusHours(1).toKotlinLocalDateTime() to requestedTime.plusHours(1).toKotlinLocalDateTime()

        val isFree = reservationDataService.isTableAvailableInTimeWindow(request.numberOfPersons, request.tableNumber, timeWindow)

        if (isFree.first)
            reservationDataService.reserveTable(request.toModel())

        return isFree
    }

    suspend fun deleteReservationById(id: Int) {
        reservationDataService.deleteReservationById(id)
    }
}