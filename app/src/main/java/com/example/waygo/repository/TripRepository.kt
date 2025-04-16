package com.example.waygo.repository

import com.example.waygo.models.Trip
import java.util.concurrent.CopyOnWriteArrayList

object TripRepository {

    // Llista segura per a múltiples fils
    private val trips = CopyOnWriteArrayList<Trip>()

    fun getAllTrips(): List<Trip> = trips.toList()

    fun getTripById(id: String): Trip? = trips.find { it.id == id }

    fun addTrip(trip: Trip) {
        trips.add(trip)
    }

    fun updateTrip(updatedTrip: Trip) {
        val index = trips.indexOfFirst { it.id == updatedTrip.id }
        if (index != -1) {
            trips[index] = updatedTrip
        }
    }

    fun deleteTrip(id: String) {
        trips.removeIf { it.id == id }
    }






}
