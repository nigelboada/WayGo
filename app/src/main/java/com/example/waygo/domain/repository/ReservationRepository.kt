package com.example.waygo.domain.repository

import com.example.waygo.domain.model.Reservation
import com.example.waygo.data.local.entity.ReservationEntity

interface ReservationRepository {
    suspend fun saveReservation(reservation: Reservation)
    suspend fun getReservationsForTrip(tripId: String): List<Reservation>
    suspend fun getAllReservationsForUser(userId: String): List<ReservationEntity>
}
