package com.example.waygo.domain.model

import com.example.waygo.di.dto.HotelDto

data class HotelResponse(
    val data: List<HotelDto>?
)

data class Hotel(
    val id: String,
    val name: String,
    val location: String,
    val rating: Double,
    val imageUrl: String,
    val pricePerNight: Double
)
