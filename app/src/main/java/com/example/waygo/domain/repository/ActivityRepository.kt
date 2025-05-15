package com.example.waygo.domain.repository

import androidx.compose.runtime.mutableStateListOf
import com.example.waygo.domain.model.Itinerary

object ActivityRepository {
    private val itineraryItems = mutableStateListOf<Itinerary>()

    fun getItemsForTrip(tripId: String): List<Itinerary> {
        return itineraryItems.filter { it.tripId == tripId }
    }

    fun addItem(item: Itinerary) {
        itineraryItems.add(item)
    }

    fun updateItem(updated: Itinerary) {
        val index = itineraryItems.indexOfFirst { it.id == updated.id }
        if (index != -1) itineraryItems[index] = updated
    }

    fun deleteItem(itemId: String) {
        itineraryItems.removeIf { it.id == itemId }
    }

    fun getActivityById(id: String): Itinerary? {
        return itineraryItems.find { it.id == id }
    }

    fun getAllActivities(): List<Itinerary> {
        return itineraryItems
    }

}