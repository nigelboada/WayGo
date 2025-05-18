package com.example.waygo.data.remote.dto

data class TripDto(
    val id: String,
    val title: String,
    val description: String,
    val location: String,
    val startDate: String,
    val endDate: String
    // No incloem activities aquí, només si l’API ho retorna
)