package com.example.waygo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "images")
data class ImageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val url: String,
    val description: String,
    val timestamp: Date = Date()
)