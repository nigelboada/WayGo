package com.example.waygo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "trips")
data class Trip(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val location: String,
    val startDate: String,
    val endDate: String,
    val activities: List<Itinerary> = emptyList() // <- AIXÒ ÉS IMPORTANT!

)
