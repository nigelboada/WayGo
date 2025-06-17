package com.example.waygo.domain.repository

import com.example.waygo.data.remote.model.Hotel
import com.example.waygo.data.remote.model.ReserveRequest
import com.example.waygo.domain.model.Reservation

interface HotelRepository {

    suspend fun getAllReservations(): Map<String, List<Reservation>>

    suspend fun cancelById(reservationId: String)
    suspend fun getGroupReservations(groupId: String, guestEmail: String): List<Reservation>


    suspend fun getHotels(groupId: String): List<Hotel>
    suspend fun reserveRoom(groupId: String, request: ReserveRequest): Boolean
    suspend fun cancelReservation(groupId: String, request: ReserveRequest): Boolean
    suspend fun getAvailability(
        groupId: String,
        startDate: String,
        endDate: String,
        hotelId: String? = null,
        city: String? = null
    ): List<Hotel>
}
