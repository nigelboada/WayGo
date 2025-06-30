package com.example.waygo.domain.repository

import com.example.waygo.domain.model.Trip

interface TripRepository {
    suspend fun getAllTripsForUser(userId: String): List<Trip>
    suspend fun addTrip(trip: Trip, userId: String)
    suspend fun updateTrip(trip: Trip, userId: String)
    suspend fun deleteTrip(tripId: String)
    suspend fun getTripById(tripId: String): Trip?

    suspend fun addImagesToTrip(tripId: String, imageUris: List<String>)
    suspend fun getImagesForTrip(tripId: String): List<String>

    suspend fun deleteImageFromTrip(tripId: String, uri: String)

}
