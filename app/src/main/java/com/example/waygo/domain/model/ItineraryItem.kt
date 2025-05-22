package com.example.waygo.domain.model

data class ItineraryItem(
    val id: String = "",
    val tripId: String,
    val title: String,
    val description: String,
    val day: String,   // Format esperat: "yyyy-MM-dd"
    val hour: String   // Format esperat: "HH:mm"
)
