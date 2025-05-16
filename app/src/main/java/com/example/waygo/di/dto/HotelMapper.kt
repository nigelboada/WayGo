package com.example.waygo.di.dto

import com.example.waygo.domain.model.Hotel

fun HotelDto.toHotel(): Hotel {
    return Hotel(
        id = locationId ?: "",
        name = name ?: "Sense nom",
        address = locationString ?: "Ubicació desconeguda",
        rating = rating?.toIntOrNull() ?: 0,  // <- si esperes Int
        imageUrl = photo?.images?.medium?.url ?: "",
        rooms = emptyList()  // Aquí està la correcció important
    )
}

