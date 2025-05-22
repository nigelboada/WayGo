package com.example.waygo.domain.repository


import com.example.waygo.domain.model.Trip
import com.example.waygo.domain.model.ItineraryItem
import com.example.waygo.domain.model.AIRecommendations
import java.util.Calendar

object TripRepository2 {
    val trips = listOf(
        Trip(
            id = 1,
            destination = "Rome, Italy",
            startDate = Calendar.getInstance().apply { set(2024, Calendar.JUNE, 12) }.time,
            endDate = Calendar.getInstance().apply { set(2024, Calendar.JUNE, 22) }.time,
            budget = 2500.0,
            notes = "hola",
            isFavorite = true,
            coverImageUrl = "h",
            itineraryItems = listOf(
                ItineraryItem(
                    id = 1,
                    tripId = 1,
                    description = "Colosseum Guided Tour",
                    date = Calendar.getInstance().apply { set(2024, Calendar.JUNE, 13) }.time,
                    location = "Rome"
                )
            ),
            userId = 123
        ),
        Trip(
            id = 2,
            destination = "Cairo, Egypt",
            startDate = Calendar.getInstance().apply { set(2024, Calendar.AUGUST, 3) }.time,
            endDate = Calendar.getInstance().apply { set(2024, Calendar.AUGUST, 13) }.time,
            budget = 1800.0,
            notes = "hola",
            isFavorite = false,
            coverImageUrl = "h",
            itineraryItems = listOf(
                ItineraryItem(
                    id = 2,
                    tripId = 2,
                    description = "Sunset at the Pyramids of Giza",
                    date = Calendar.getInstance().apply { set(2024, Calendar.AUGUST, 4) }.time,
                    location = "Cairo"
                )
            ),
            userId = 123
        ),
        Trip(
            id = 3,
            destination = "Sydney, Australia",
            startDate = Calendar.getInstance().apply { set(2024, Calendar.NOVEMBER, 5) }.time,
            endDate = Calendar.getInstance().apply { set(2024, Calendar.NOVEMBER, 15) }.time,
            budget = 3200.0,
            notes = "hola",
            isFavorite = true,
            coverImageUrl = "h",
            itineraryItems = listOf(
                ItineraryItem(
                    id = 3,
                    tripId = 3,
                    description = "Sydney Opera House Backstage Tour",
                    date = Calendar.getInstance().apply { set(2024, Calendar.NOVEMBER, 6) }.time,
                    location = "Sydney"
                )
            ),
            userId = 123
        )
    )

    val itineraryItems = listOf(
        ItineraryItem(
            id = 1,
            tripId = 1,
            description = "Colosseum Guided Tour",
            date = Calendar.getInstance().apply { set(2024, Calendar.JUNE, 13) }.time,
            location = "Rome"
        ),
        ItineraryItem(
            id = 2,
            tripId = 2,
            description = "Sunset at the Pyramids of Giza",
            date = Calendar.getInstance().apply { set(2024, Calendar.AUGUST, 4) }.time,
            location = "Cairo"
        ),
        ItineraryItem(
            id = 3,
            tripId = 3,
            description = "Sydney Opera House Backstage Tour",
            date = Calendar.getInstance().apply { set(2024, Calendar.NOVEMBER, 6) }.time,
            location = "Sydney"
        )
    )

    val aiRecommendations = listOf(
        AIRecommendations(
            id = 1,
            tripId = 1,
            suggestedActivity = "Try authentic Italian gelato near the Trevi Fountain",
            rating = 4.9
        ),
        AIRecommendations(
            id = 2,
            tripId = 2,
            suggestedActivity = "Explore the Egyptian Museum for ancient artifacts",
            rating = 4.7
        ),
        AIRecommendations(
            id = 3,
            tripId = 3,
            suggestedActivity = "Take a ferry to Manly Beach for a scenic view of Sydney",
            rating = 4.8
        )
    )
}