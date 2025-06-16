package com.example.waygo.domain.model

import com.example.waygo.data.remote.model.Hotel
import com.example.waygo.data.remote.model.Room

data class Reservation(
    val id: String,
    val hotelId: String,
    val roomId: String,
    val startDate: String,
    val endDate: String,
    val guestName: String,
    val guestEmail: String,
    val hotel: Hotel,
    val room: Room
)
