package persistence.entity

import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDateTime

@Serializable
data class ReservationEntity(
    val name: String,
    val surname: String,
    val tableId: Int,
    val numberOfPersons: Int,
    val date: LocalDateTime
)
