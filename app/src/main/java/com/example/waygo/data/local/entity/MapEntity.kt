package com.example.waygo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "map")
class MapEntity (
    @PrimaryKey
    val location: String,
    val latitude: Double,
    val longitude: Double
)