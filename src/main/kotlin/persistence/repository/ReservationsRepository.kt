package cz.monetplus.persistence.repository

import cz.monetplus.persistence.entity.ReservationEntity
import kotlinx.datetime.LocalDateTime

interface ReservationsRepository {

    suspend fun getAllReservations(): List<ReservationEntity>

    suspend fun reserveTable(reservationEntity: ReservationEntity)

    suspend fun deleteReservationById(id: Int)

    suspend fun isTableAvailableInTimeWindow(seats: Int, tableId: Int, timeWindow: Pair<LocalDateTime, LocalDateTime>): Pair<Boolean, String>

}