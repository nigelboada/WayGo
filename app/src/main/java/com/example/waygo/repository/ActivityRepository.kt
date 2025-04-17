package com.example.waygo.repository

import androidx.compose.runtime.mutableStateListOf
import com.example.waygo.models.Activity

object ActivityRepository {
    private val itineraryItems = mutableStateListOf<Activity>()

    fun getItemsForTrip(tripId: String): List<Activity> {
        return itineraryItems.filter { it.tripId == tripId }
    }

    fun addItem(item: Activity) {
        itineraryItems.add(item)
    }

    fun updateItem(updated: Activity) {
        val index = itineraryItems.indexOfFirst { it.id == updated.id }
        if (index != -1) itineraryItems[index] = updated
    }

    fun deleteItem(itemId: String) {
        itineraryItems.removeIf { it.id == itemId }
    }

    fun getActivityById(id: String): Activity? {
        return itineraryItems.find { it.id == id }
    }

    fun getAllActivities(): List<Activity> {
        return itineraryItems
    }

}
