package com.example.waygo.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trip_images")
data class TripImageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tripId: String,
    val imageUri: String   // emmagatzemarem la URI/String de l’arxiu
)