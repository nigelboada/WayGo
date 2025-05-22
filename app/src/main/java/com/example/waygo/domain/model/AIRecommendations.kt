package com.example.waygo.domain.model

data class AIRecommendations(
    val id: Int = 0,
    val tripId: Int,
    val suggestedActivity: String,
    val rating: Double
)
