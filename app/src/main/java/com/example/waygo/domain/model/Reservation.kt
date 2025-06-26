package com.example.waygo.domain.model

import com.example.waygo.data.remote.model.Hotel
import com.example.waygo.data.remote.model.Room

data class Reservation(
    val id: String,
    val tripId: String,
    val hotelId: String,
    val hotelName: String,
    val roomId: String,
    val roomType: String,
    val price: Float,
    val startDate: String,
    val endDate: String,
    val guestEmail: String,
    val hotelImageUrl: String,   // ← nova
    val roomImageUrl: String     // ← nova
)

