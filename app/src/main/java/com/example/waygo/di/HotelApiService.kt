package com.example.waygo.di

import com.example.waygo.domain.model.HotelResponse
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

// HotelApiService.kt
interface HotelApiService {

    @GET("hotels/list-by-latlng")
    suspend fun getHotels(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("limit") limit: Int = 10,
        @Header("X-RapidAPI-Key") apiKey: String,
        @Header("X-RapidAPI-Host") apiHost: String = "travel-advisor.p.rapidapi.com"
    ): Response<HotelResponse>
}

