package com.example.waygo.models

data class Hotel(
    val id: String,
    val name: String,
    val location: String,
    val rating: Double,
    val imageUrl: String,
    val pricePerNight: Double
)
