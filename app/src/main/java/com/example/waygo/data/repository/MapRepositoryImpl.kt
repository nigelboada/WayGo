package com.example.waygo.data.repository

import com.example.waygo.domain.model.MapLocation
import javax.inject.Inject
import javax.inject.Singleton
import com.example.waygo.domain.repository.MapRepository

@Singleton
class MapRepositoryImpl @Inject constructor() : MapRepository {

    private val currentLocation = MapLocation("Ciudad Actual", 40.4168, -3.7038) // Això és Madrid

    override fun getCurrentLocation(): MapLocation? {
        return MapLocation("Ciudad Actual", 40.4168, -3.7038)
    }

    override fun getNearbyPlaces(latitude: Double, longitude: Double): List<MapLocation> {
        return listOf(
            MapLocation("Parque Central", latitude + 0.01, longitude + 0.01),
            MapLocation("Museo Histórico", latitude - 0.02, longitude + 0.015),
            MapLocation("Cafetería Popular", latitude + 0.005, longitude - 0.01)
        )
    }
}