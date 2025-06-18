package com.example.waygo.domain.repository

import com.example.waygo.data.remote.TripApiService
import com.example.waygo.data.local.dao.TripDao
import com.example.waygo.data.local.mapper.toTrip
import com.example.waygo.data.local.mapper.toTripEntity
import com.example.waygo.domain.model.Trip
import javax.inject.Inject

class TripRepository @Inject constructor(
    private val tripDao: TripDao,
    private val tripApi: TripApiService // ara l’injectes
) {

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

    // aquí podràs usar tripApi per fer crides a la xarxa, si cal
}
