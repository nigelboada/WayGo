package com.example.waygo.di.dto

data class HotelDto(
    val id: String,
    val name: String,
    val location: String,
    val rating: Double,
    val imageUrl: String,
    val pricePerNight: Double
)
