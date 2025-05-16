package com.example.waygo.di

import com.example.waygo.domain.model.Hotel
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Path

// HotelApiService.kt
interface HotelApiService {
    @GET("hotels/{group_id}/hotels")
    suspend fun getHotels(
        @Path("group_id") groupId: String = "G05"
    ): Response<List<Hotel>>
}


