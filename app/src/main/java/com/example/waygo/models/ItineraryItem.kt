package com.example.waygo.models

import java.util.UUID

data class ItineraryItem(
    val id: String = UUID.randomUUID().toString(),
    val tripId: String,  // Relació amb el viatge
    val title: String,
    val description: String,
    val time: String  // o LocalDateTime si vols treballar amb dates reals
)
