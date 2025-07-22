package requests

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import persistence.entity.ReservationEntity

@Serializable
data class ReservationRequest(
    val name: String,
    val surname: String,
    val tableNumber: Int,
    val numberOfPersons: Int,
    val date: LocalDateTime,
)

fun ReservationRequest.toModel(): ReservationEntity =
    ReservationEntity(this.name, this.surname, this.tableNumber, this.numberOfPersons, this.date)