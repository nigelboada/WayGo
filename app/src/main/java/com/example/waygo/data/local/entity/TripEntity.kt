package com.example.waygo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val destination: String,
    val startDate: Long,
    val endDate: Long,
    val budget: Double,
    val notes: String,
    val isFavorite: Boolean,
    val coverImageUrl: String,
)