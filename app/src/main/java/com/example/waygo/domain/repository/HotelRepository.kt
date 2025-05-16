package com.example.waygo.domain.repository


import com.example.waygo.domain.model.Hotel
import com.example.waygo.di.HotelApiService


class HotelRepository(private val apiService: HotelApiService) {
    suspend fun getHotels(): List<Hotel>? {
        return try {
            val response = apiService.getHotels()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}

