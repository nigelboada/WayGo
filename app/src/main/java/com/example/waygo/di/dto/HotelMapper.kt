package com.example.waygo.di.dto

import com.example.waygo.models.Hotel

fun HotelDto.toHotel(): Hotel {
    return Hotel(
        id = id,
        name = name,
        location = location,
        rating = rating,
        imageUrl = imageUrl,
        pricePerNight = pricePerNight
    )
}
