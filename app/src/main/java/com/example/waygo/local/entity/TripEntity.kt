package com.example.waygo.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey val id: String, // era Int o convertit a partir d'Int
    val title: String,
    val description: String,
    val location: String,
    val startDate: String,
    val endDate: String,
    val userId: String
)
