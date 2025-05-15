package com.example.waygo.repository

import com.example.waygo.di.RetrofitClient
import com.example.waygo.di.dto.toHotel
import com.example.waygo.models.Hotel
import com.example.waygo.di.HotelApiService


class HotelRepository {

    private val api = RetrofitClient.instance.create(HotelApiService::class.java)

    suspend fun getHotels(): List<Hotel> {
        return api.getHotels().map { it.toHotel() }
    }
}
