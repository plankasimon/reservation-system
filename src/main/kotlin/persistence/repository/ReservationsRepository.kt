package persistence.repository

import kotlinx.datetime.LocalDateTime
import persistence.entity.ReservationEntity

interface ReservationsRepository {

    suspend fun getAllReservations(): List<ReservationEntity>

    suspend fun reserveTable(reservationEntity: ReservationEntity)

    suspend fun deleteReservationById(id: Int)

    suspend fun isTableAvailableInTimeWindow(seats: Int, tableId: Int, timeWindow: Pair<LocalDateTime, LocalDateTime>): Pair<Boolean, String>

}