package com.example.waygo.di

import com.example.waygo.domain.model.Hotel
import com.example.waygo.domain.model.ReserveRequest
import retrofit2.Response
import retrofit2.http.*

interface TripApiService {

    @GET("hotels/{group_id}/availability")
    suspend fun checkAvailability(
        @Path("group_id") groupId: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("hotel_id") hotelId: String? = null,
        @Query("city") city: String? = null
    ): Response<Any>

    // Altres mètodes els afegirem al T1.2
}
