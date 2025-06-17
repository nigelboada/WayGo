package com.example.waygo.domain.repository

import com.example.waygo.data.remote.RetrofitClient
import com.example.waygo.data.remote.TripApiService
import com.example.waygo.data.local.dao.TripDao
import com.example.waygo.data.local.mapper.toTrip
import com.example.waygo.data.local.mapper.toTripEntity
import com.example.waygo.domain.model.Trip

class TripRepository(private val tripDao: TripDao) {

    suspend fun getAllTripsForUser(userId: String): List<Trip> {
        return tripDao.getTripsByUser(userId).map { it.toTrip() }
    }

    suspend fun addTrip(trip: Trip, userId: String) {
        tripDao.insertTrip(trip.toTripEntity(userId))
    }

    suspend fun updateTrip(trip: Trip, userId: String) {
        tripDao.updateTrip(trip.toTripEntity(userId))
    }

    suspend fun deleteTrip(tripId: String) {
        tripDao.getTripById(tripId)?.let { tripDao.deleteTrip(it) }
    }

    suspend fun getTripById(tripId: String): Trip? {
        return tripDao.getTripById(tripId)?.toTrip()
    }


    private val api = RetrofitClient.retrofit.create(TripApiService::class.java)





}
