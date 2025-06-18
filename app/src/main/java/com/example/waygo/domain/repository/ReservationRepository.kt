package com.example.waygo.domain.repository

import com.example.waygo.data.local.dao.ReservationDao
import com.example.waygo.data.local.entity.ReservationEntity
import javax.inject.Inject

class ReservationRepository @Inject constructor(
    private val dao: ReservationDao
) {
    suspend fun saveReservation(res: ReservationEntity) {
        dao.insert(res)
    }

    suspend fun getReservationsForTrip(tripId: String): List<ReservationEntity> {
        return dao.getReservationsForTrip(tripId)
    }
}
