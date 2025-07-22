package cz.monetplus.persistence.mapping

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

private const val TABLES_TABLE_NAME = "reservation_system.tables"

object TablesTable : IntIdTable(TABLES_TABLE_NAME) {
    val seatsAvailable = integer("seats_available")
}

class TablesDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TablesDao>(TablesTable)

    var seatsAvailable by TablesTable.seatsAvailable
}

