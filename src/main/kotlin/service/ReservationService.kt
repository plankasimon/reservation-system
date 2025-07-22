package service

import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import persistence.entity.ReservationEntity
import persistence.service.ReservationDataService
import requests.ReservationRequest
import requests.toModel

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