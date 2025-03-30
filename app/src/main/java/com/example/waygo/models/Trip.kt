package com.example.waygo.models

data class Trip(
    val id: String,
    val destination: String,
    val startDate: String,
    val endDate: String,
    val itinerary: List<Itinerary>
)
