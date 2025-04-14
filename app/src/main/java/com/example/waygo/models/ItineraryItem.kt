package com.example.waygo.models

import java.util.UUID

data class ItineraryItem(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val time: String, // També es pot fer servir LocalTime o Date
    val location: String
)
