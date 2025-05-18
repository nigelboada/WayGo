package com.example.waygo.di

import com.example.waygo.data.remote.dto.TripDto
import retrofit2.http.GET
import retrofit2.http.Path

interface TripApiService {
    @GET("trips")
    suspend fun getTrips(): List<TripDto> // defineix el model DTO

    @GET("trips/{id}")
    suspend fun getTrip(@Path("id") id: String): TripDto
}