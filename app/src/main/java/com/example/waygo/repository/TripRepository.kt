package com.example.waygo.repository

import android.util.Log
import com.example.waygo.models.Trip
import java.util.concurrent.CopyOnWriteArrayList

object TripRepository {

    // Llista segura per a múltiples fils
    private val trips = CopyOnWriteArrayList<Trip>()

    fun getAllTrips(): List<Trip> = trips.toList()

    fun getTripById(id: String): Trip? {
        val trip = trips.find { it.id == id }
        Log.d("TripRepository", "Viatge recuperat: $trip") // Afegeix log per veure si es troba el viatge
        return trip
    }

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
