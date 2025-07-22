package persistence.mapping

import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.datetime.datetime
import persistence.entity.ReservationEntity

private const val RESERVATION_TABLE_NAME = "reservation_system.reservation"

object ReservationTable : IntIdTable(RESERVATION_TABLE_NAME) {
    val name = varchar("name", 50)
    val surname = varchar("surname", 50)
    val tableId = optReference("table_id", TablesTable.id, onDelete = ReferenceOption.RESTRICT)
    val numberOfPersons = integer("number_of_persons")
    val date = datetime("date")
}

class ReservationDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ReservationDAO>(ReservationTable)
    var name by ReservationTable.name
    var surname by ReservationTable.surname
    var tableId by ReservationTable.tableId
    var numberOfPersons by ReservationTable.numberOfPersons
    var date by ReservationTable.date
}

fun ReservationDAO.toReservationEntity(): ReservationEntity =
    ReservationEntity(
        name = this.name,
        surname = this.surname,
        tableId = this.tableId?.value ?: error("Reservation must be linked to a table"),
        numberOfPersons = this.numberOfPersons,
        date = this.date
    )
