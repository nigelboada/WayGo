package com.example.waygo.domain.model

import com.example.waygo.di.dto.HotelDto

data class Hotel(
    val id: String,
    val name: String,
    val address: String,
    val rating: Int,
    val rooms: List<Room>,
    val imageUrl: String
)

data class Room(
    val id: String,
    val roomType: String,
    val price: Double,
    val images: List<String>
)

