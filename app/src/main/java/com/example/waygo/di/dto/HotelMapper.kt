package com.example.waygo.di.dto

import com.example.waygo.domain.model.Hotel

fun HotelDto.toHotel(): Hotel {
    return Hotel(
        id = locationId ?: "",
        name = name ?: "Sense nom",
        location = locationString ?: "Ubicació desconeguda",
        rating = rating?.toDoubleOrNull() ?: 0.0,
        imageUrl = photo?.images?.medium?.url ?: "",
        pricePerNight = price?.filter { it.isDigit() || it == '.' }?.toDoubleOrNull() ?: 0.0
    )
}

