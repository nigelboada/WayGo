package com.example.waygo.models

import com.example.waygo.models.ItineraryItem
import java.util.UUID

data class Trip(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val startDate: String,  // Es pot millorar amb tipus Date més endavant
    val endDate: String,
    val location: String,
    val itinerary: List<ItineraryItem> = emptyList()
)
