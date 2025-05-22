package com.example.waygo.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "itineraries",
    foreignKeys = [ForeignKey(
        entity = TripEntity::class,
        parentColumns = ["id"],
        childColumns = ["tripId"],
        onDelete = ForeignKey.CASCADE
    )]
)

data class ItineraryItemEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tripId: Int,
    val description: String,
    val date: Long,
    val location: String,
)