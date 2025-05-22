package com.example.waygo.domain.repository

import com.example.waygo.domain.model.Trip

interface TripRepository {
    suspend fun addTrip(trip: Trip): Boolean
    suspend fun getTripById(id: Int): Trip?
    suspend fun getAllTrips(): List<Trip>
    suspend fun updateTrip(trip: Trip): Boolean
    suspend fun deleteTrip(id: Int): Boolean
    suspend fun getTripsByUserId(userId: Int): List<Trip>
}