package com.example.waygo.repository

import androidx.compose.runtime.mutableStateListOf
import com.example.waygo.models.ItineraryItem

object ActivityRepository {
    private val itineraryItems = mutableStateListOf<ItineraryItem>()

    fun getItemsForTrip(tripId: String): List<ItineraryItem> {
        return itineraryItems.filter { it.tripId == tripId }
    }

    fun addItem(item: ItineraryItem) {
        itineraryItems.add(item)
    }

    fun updateItem(updated: ItineraryItem) {
        val index = itineraryItems.indexOfFirst { it.id == updated.id }
        if (index != -1) itineraryItems[index] = updated
    }

    fun deleteItem(itemId: String) {
        itineraryItems.removeIf { it.id == itemId }
    }
}
