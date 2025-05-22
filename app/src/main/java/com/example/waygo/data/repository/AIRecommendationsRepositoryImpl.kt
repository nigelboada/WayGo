package com.example.waygo.data.repository

import android.util.Log
import com.example.waygo.domain.model.AIRecommendations
import com.example.waygo.domain.repository.AIRecommendationsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AIRecommendationsRepositoryImpl @Inject constructor(): AIRecommendationsRepository {

    private val recommendations = mutableListOf<AIRecommendations>()
    private val TAG = "AIRecommendationsRepo" // Definir un tag para los logs

    override fun getRecommendations(): List<AIRecommendations> {
        Log.d(TAG, "Obteniendo recomendaciones. Total de recomendaciones: ${recommendations.size}")
        return recommendations
    }

    override fun generateRecommendation(tripId: Int, activity: String, rating: Double) {
        Log.d(TAG, "Generando recomendación para el viaje: $tripId, actividad: $activity, calificación: $rating")

        val newRecommendation = AIRecommendations(
            id = recommendations.size + 1,
            tripId = tripId,
            suggestedActivity = activity,
            rating = rating
        )
        recommendations.add(newRecommendation)

        Log.i(TAG, "Recomendación generada y agregada: ${newRecommendation}")
    }

    override fun deleteRecommendation(recommendationId: Int) {
        Log.d(TAG, "Eliminando recomendación con ID: $recommendationId")

        val initialSize = recommendations.size
        recommendations.removeAll { it.id == recommendationId }

        if (recommendations.size < initialSize) {
            Log.i(TAG, "Recomendación con ID $recommendationId eliminada correctamente.")
        } else {
            Log.w(TAG, "No se encontró la recomendación con ID $recommendationId para eliminar.")
        }
    }

    override fun updateRecommendation(recommendation: AIRecommendations) {
        Log.d(TAG, "Actualizando recomendación con ID: ${recommendation.id}")

        val index = recommendations.indexOfFirst { it.id == recommendation.id }
        if (index != -1) {
            recommendations[index] = recommendation
            Log.i(TAG, "Recomendación con ID ${recommendation.id} actualizada: $recommendation")
        } else {
            Log.w(TAG, "No se encontró la recomendación con ID ${recommendation.id} para actualizar.")
        }
    }

    override fun rateRecommendation(recommendationId: Int, rating: Double) {
        Log.d(TAG, "Calificando recomendación con ID: $recommendationId con una calificación de: $rating")

        val index = recommendations.indexOfFirst { it.id == recommendationId }
        if (index != -1) {
            recommendations[index] = recommendations[index].copy(rating = rating)
            Log.i(TAG, "Recomendación con ID $recommendationId actualizada con la nueva calificación: $rating")
        } else {
            Log.w(TAG, "No se encontró la recomendación con ID $recommendationId para calificar.")
        }
    }
}
