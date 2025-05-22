package com.example.waygo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ai_recommendations")
data class AIRecommendationsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tripId: String,
    val suggestedActivity: String,
    val rating: Double
)