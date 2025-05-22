package com.example.waygo.domain.repository

import com.example.waygo.domain.model.ItineraryItem

interface ItineraryRepository {

    suspend fun addItineraryItem(item: ItineraryItem): Boolean
    suspend fun getItineraryItems(tripId: Int): List<ItineraryItem>
    suspend fun getItineraryItemById(id: Int): ItineraryItem?
    suspend fun updateItineraryItem(item: ItineraryItem): Boolean
    suspend fun deleteItineraryItem(id: Int): Boolean

}