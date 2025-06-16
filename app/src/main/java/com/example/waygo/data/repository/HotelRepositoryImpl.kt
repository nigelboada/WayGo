package com.example.waygo.data.repository

import com.example.waygo.data.remote.TripApiService
import com.example.waygo.data.remote.model.Hotel
import com.example.waygo.data.remote.model.ReserveRequest
import com.example.waygo.domain.repository.HotelRepository
import javax.inject.Inject

class HotelRepositoryImpl @Inject constructor(
    private val apiService: TripApiService
) : HotelRepository {

    override suspend fun getHotels(groupId: String): List<Hotel> {
        return apiService.listHotels(groupId)
    }

    override suspend fun checkAvailability(
        groupId: String,
        startDate: String,
        endDate: String,
        hotelId: String?,
        city: String?
    ): List<Hotel> {
        return apiService.checkAvailability(
            groupId = groupId,
            startDate = startDate,
            endDate = endDate,
            hotelId = hotelId,
            city = city
        )
    }

    override suspend fun reserveRoom(groupId: String, request: ReserveRequest): Boolean {
        val response = apiService.reserveRoom(groupId, request)
        return response.isSuccessful
    }

    override suspend fun cancelReservation(groupId: String, request: ReserveRequest): Boolean {
        val response = apiService.cancelReservation(groupId, request)
        return response.isSuccessful
    }
}
