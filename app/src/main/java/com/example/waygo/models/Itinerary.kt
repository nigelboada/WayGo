package com.example.waygo.models

import java.util.UUID

data class Itinerary(

    val id: String = UUID.randomUUID().toString(),
    val tripId: String,  // Relació amb el viatge
    val title: String,
    val description: String,
    val day: String, // "2025-04-17"
    val hour: String // "15:30")
)