package com.example.waygo.domain.repository

import com.example.waygo.domain.model.Reservation

interface ReservationRepository {
    suspend fun saveReservation(reservation: Reservation)
    suspend fun getReservationsForTrip(tripId: String): List<Reservation>
    suspend fun getAllReservationsForUser(userId: String): List<Reservation>
    suspend fun deleteReservation(reservationId: String): Boolean

    suspend fun getAllReservations(): Map<String, List<Reservation>>

}
