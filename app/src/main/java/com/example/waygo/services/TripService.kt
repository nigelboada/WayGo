package com.example.waygo.services

import com.example.waygo.models.Trip

class TripService {

    private val trips = mutableListOf<Trip>()

    fun addTrip(trip: Trip) {
        trips.add(trip)
    }

    fun getTrips(): List<Trip> {
        return trips
    }

    fun getTripById(id: String): Trip? {
        return trips.find { it.id == id }
    }
}
