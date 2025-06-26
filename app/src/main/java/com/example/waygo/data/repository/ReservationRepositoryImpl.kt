// → app/src/main/java/com/example/waygo/data/repository/ReservationRepositoryImpl.kt

package com.example.waygo.data.repository

import com.example.waygo.data.local.dao.ReservationDao
import com.example.waygo.data.local.entity.ReservationEntity
import com.example.waygo.data.local.mapper.ReservationMapper
import com.example.waygo.data.remote.api.CancelRequestDto
import com.example.waygo.data.remote.api.HotelApiService
import com.example.waygo.domain.model.Reservation
import com.example.waygo.domain.repository.ReservationRepository
import javax.inject.Inject
import javax.inject.Singleton

// → app/src/main/java/com/example/waygo/data/repository/ReservationRepositoryImpl.kt

@Singleton
class ReservationRepositoryImpl @Inject constructor(
    private val dao: ReservationDao,
    private val mapper: ReservationMapper,
    private val api: HotelApiService
) : ReservationRepository {

    override suspend fun deleteReservation(reservationId: String): Boolean {
        // Recupera l'entitat
        val entity = dao.getAllReservations()
            .firstOrNull { it.id == reservationId }
            ?: return false

        // Esborra local
        dao.deleteById(reservationId)

        // Crida remota
        val request = CancelRequestDto(
            hotelId    = entity.hotelId,
            roomId     = entity.roomId,
            guestName  = entity.guestEmail,
            guestEmail = entity.guestEmail,
            startDate  = entity.startDate,
            endDate    = entity.endDate
        )
        return try {
            val resp = api.cancelReservation(entity.tripId, request)
            resp.message.contains("cancel", ignoreCase = true)
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun saveReservation(reservation: Reservation) {
        dao.insert(mapper.toEntity(reservation))
    }

    override suspend fun getReservationsForTrip(tripId: String): List<Reservation> =
        dao.getReservationsForTrip(tripId)
            .map { mapper.toDomain(it)
    }

    override suspend fun getAllReservationsForUser(userId: String): List<ReservationEntity> {
        // si realment vols filtrar per userId, afegeix-lo a l'entitat; sinó:
        return dao.getAllReservations()
    }

    override suspend fun getAllReservations(): Map<String, List<Reservation>> {
        val entities = dao.getAllReservations()
        val domains  = entities.map { mapper.toDomain(it) }
        return domains.groupBy { it.tripId }
    }
}
