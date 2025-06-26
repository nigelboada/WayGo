package com.example.waygo.domain.model

import com.example.waygo.data.remote.model.Hotel
import com.example.waygo.data.remote.model.Room

data class Reservation(
    val id: String,
    val tripId: String,      // <-- nou!
    val hotelId: String,
    val hotelName: String,   // <-- nou!
    val roomId: String,
    val roomType: String,    // <-- nou!
    val price: Float,        // <-- moure aquí
    val startDate: String,
    val endDate: String,
    val guestEmail: String,  // <-- ja hi és
    val imageUrl: String     // <-- nou!
)

