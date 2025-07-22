package cz.monetplus.persistence.service

import cz.monetplus.persistence.mapping.ReservationDAO
import cz.monetplus.persistence.mapping.ReservationTable
import cz.monetplus.persistence.entity.ReservationEntity
import cz.monetplus.persistence.mapping.TablesDao
import cz.monetplus.persistence.mapping.TablesTable
import cz.monetplus.persistence.repository.ReservationsRepository
import cz.monetplus.persistence.mapping.suspendTransaction
import cz.monetplus.persistence.mapping.toReservationEntity
import kotlinx.datetime.LocalDateTime
import mu.KotlinLogging
import org.jetbrains.exposed.v1.core.SqlExpressionBuilder.eq
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.jdbc.deleteWhere

class ReservationDataService : ReservationsRepository {

    private val logger = KotlinLogging.logger {}

    override suspend fun getAllReservations(): List<ReservationEntity> = suspendTransaction{
        ReservationDAO.all().map {it.toReservationEntity()}
    }

    override suspend fun reserveTable(reservationEntity: ReservationEntity): Unit = suspendTransaction {
        ReservationDAO.new {
            name = reservationEntity.name
            surname = reservationEntity.surname
            tableId = EntityID(reservationEntity.tableId, TablesTable)
            numberOfPersons = reservationEntity.numberOfPersons
            date = reservationEntity.date
        }
    }

    override suspend fun deleteReservationById(id: Int): Unit = suspendTransaction {
        ReservationTable.deleteWhere { ReservationTable.id eq id }
    }

    override suspend fun isTableAvailableInTimeWindow(seats: Int, tableId: Int, timeWindow: Pair<LocalDateTime, LocalDateTime>): Pair<Boolean, String> =
        isTableAvailable(seats, tableId, timeWindow)

    private suspend fun isTableAvailable(seats: Int, tableId: Int, timeWindow: Pair<LocalDateTime, LocalDateTime>): Pair<Boolean, String> = suspendTransaction {
        val table = TablesDao.find { TablesTable.id eq tableId }.firstOrNull()
        if (table == null) {
            logger.info { "Table with id $tableId not found" }
            return@suspendTransaction false to "Table with id $tableId not found"
        }

        if (table.seatsAvailable < seats) {
            logger.info { "Too many seats requested: ${table.seatsAvailable} < $seats" }
            return@suspendTransaction false to "Too many seats requested: ${table.seatsAvailable} < $seats"
        }

        val overlappingReservationExists = ReservationDAO.find {
            (ReservationTable.tableId eq tableId) and
            ((ReservationTable.date greaterEq timeWindow.first) and (ReservationTable.date less timeWindow.second))
        }.any()

        if (overlappingReservationExists) {
            logger.info { "Table is not available in time window from ${timeWindow.first} to ${timeWindow.second}" }
            false to "Table is not available in time window from ${timeWindow.first} to ${timeWindow.second}"
        } else {
            true to ""
        }

    }

}