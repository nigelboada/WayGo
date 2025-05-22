package com.example.waygo.domain.repository

import com.example.waygo.domain.model.MapLocation

interface MapRepository {
    fun getCurrentLocation(): MapLocation?
    fun getNearbyPlaces(latitude: Double, longitude: Double): List<MapLocation>
}