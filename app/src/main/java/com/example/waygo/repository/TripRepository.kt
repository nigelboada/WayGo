package com.example.waygo.repository

import com.example.waygo.models.ItineraryItem
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


    fun addActivityToTrip(tripId: String, activity: ItineraryItem) {
        val trip = getTripById(tripId)
        trip?.let {
            val updatedTrip = it.copy(itinerary = it.itinerary + activity)
            updateTrip(updatedTrip)
        }
    }

    fun updateActivityInTrip(tripId: String, activity: ItineraryItem) {
        val trip = getTripById(tripId)
        trip?.let {
            val updatedItinerary = it.itinerary.map { item ->
                if (item.id == activity.id) activity else item
            }
            val updatedTrip = it.copy(itinerary = updatedItinerary)
            updateTrip(updatedTrip)
        }
    }

    fun deleteActivityFromTrip(tripId: String, activityId: String) {
        val trip = getTripById(tripId)
        trip?.let {
            val updatedItinerary = it.itinerary.filterNot { item ->
                item.id == activityId
            }
            val updatedTrip = it.copy(itinerary = updatedItinerary)
            updateTrip(updatedTrip)
        }
    }




}
