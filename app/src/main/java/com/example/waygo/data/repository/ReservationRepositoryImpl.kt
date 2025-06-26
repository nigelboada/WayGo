// → app/src/main/java/com/example/waygo/data/repository/ReservationRepositoryImpl.kt

package com.example.waygo.data.repository

import com.example.waygo.data.local.dao.ReservationDao
import com.example.waygo.data.local.mapper.ReservationMapper
import com.example.waygo.domain.model.Reservation
import com.example.waygo.domain.repository.ReservationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReservationRepositoryImpl @Inject constructor(
    private val dao: ReservationDao,
    private val mapper: ReservationMapper
) : ReservationRepository {

    override suspend fun saveReservation(reservation: Reservation) {
        dao.insert(mapper.toEntity(reservation))
    }

    override suspend fun getReservationsForTrip(tripId: String): List<Reservation> {
        return dao.getReservationsForTrip(tripId)
            .map { mapper.toDomain(it) }
    }
}
