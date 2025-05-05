package com.example.waygo.repository

import com.example.waygo.local.dao.TripDao
import com.example.waygo.local.entity.TripEntity
import com.example.waygo.models.Trip

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

    private fun TripEntity.toTrip() = Trip(
        id = id,
        title = title,
        description = description,
        startDate = startDate,
        endDate = endDate,
        location = location
    )

    private fun Trip.toTripEntity(userId: String) = TripEntity(
        id = id,
        title = title,
        description = description,
        location = location,
        startDate = startDate,
        endDate = endDate,
        userId = userId
    )
}
