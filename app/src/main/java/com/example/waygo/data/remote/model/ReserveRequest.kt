package com.example.waygo.data.remote.model

data class ReserveRequest(
    val hotelId: String,
    val roomId: String,
    val startDate: String,
    val endDate: String,
    val guestName: String,
    val guestEmail: String
)