package com.example.waygo.domain.repository


import com.example.waygo.domain.model.AIRecommendations

interface AIRecommendationsRepository {
    fun getRecommendations(): List<AIRecommendations>
    fun generateRecommendation(tripId: Int, activity: String, rating: Double)
    fun deleteRecommendation(recommendationId: Int)
    fun updateRecommendation(recommendation: AIRecommendations)
    fun rateRecommendation(recommendationId: Int, rating: Double)
}