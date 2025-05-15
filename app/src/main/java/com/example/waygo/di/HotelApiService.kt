package com.example.waygo.di

import com.example.waygo.di.dto.HotelDto
import retrofit2.http.GET

interface HotelApiService {
    @GET("hotels")
    suspend fun getHotels(): List<HotelDto>
}
