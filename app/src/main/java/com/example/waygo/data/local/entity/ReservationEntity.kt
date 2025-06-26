package com.example.waygo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservations")
data class ReservationEntity(
    @PrimaryKey val id: String,
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
