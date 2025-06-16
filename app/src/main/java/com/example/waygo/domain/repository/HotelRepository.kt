package com.example.waygo.domain.repository

import com.example.waygo.data.remote.model.Hotel
import com.example.waygo.data.remote.model.ReserveRequest
import com.example.waygo.data.remote.model.Room
import java.time.LocalDate

interface HotelRepository {
    suspend fun getHotels(groupId: String): List<Hotel>
    suspend fun checkAvailability(
        groupId: String,
        startDate: String,
        endDate: String,
        hotelId: String? = null,
        city: String? = null
    ): List<Hotel> // o una altra resposta si saps l’estructura
    suspend fun reserveRoom(groupId: String, request: ReserveRequest): Boolean
    suspend fun cancelReservation(groupId: String, request: ReserveRequest): Boolean
}
