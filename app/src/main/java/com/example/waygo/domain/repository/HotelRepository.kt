package com.example.waygo.domain.repository

import com.example.waygo.di.RetrofitClient
import com.example.waygo.di.dto.toHotel
import com.example.waygo.domain.model.Hotel
import com.example.waygo.di.HotelApiService


class HotelRepository {

    private val api = RetrofitClient.instance.create(HotelApiService::class.java)

    suspend fun getHotels(): List<Hotel> {
        val response = api.getHotels(
            lat = 41.3851,
            lon = 2.1734,
            apiKey = "c9ba0c5aa4msh433a93b2bd13fadp1b988djsnd9c16398f829",  // posa la teva API Key real aquí
            apiHost = "travel-advisor.p.rapidapi.com"
        )
        if (response.isSuccessful) {
            return response.body()?.data?.map { it.toHotel() } ?: emptyList()
        } else {
            throw Exception("Error HTTP: ${response.code()} - ${response.message()}")
        }
    }
}
