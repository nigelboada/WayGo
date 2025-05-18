package com.example.waygo.domain.repository

import com.example.waygo.di.RetrofitClient
import com.example.waygo.di.TripApiService
import com.example.waygo.data.local.dao.TripDao
import com.example.waygo.data.local.mapper.toTrip
import com.example.waygo.data.remote.mapper.toTrip
import com.example.waygo.data.local.mapper.toTripEntity
import com.example.waygo.domain.model.Trip

class TripRepository(private val tripDao: TripDao) {

    suspend fun getAllTripsForUser(userId: String): List<Trip> {
        return tripDao.getTripsByUser(userId).map { it.toTrip() }
    }

    suspend fun addTrip(trip: Trip, userId: String) {
        tripDao.insertTrip(trip.toTripEntity(userId))
    }

    suspend fun updateTrip(trip: Trip, userId: String) {
        tripDao.updateTrip(trip.toTripEntity(userId))
    }

    suspend fun deleteTrip(tripId: String) {
        tripDao.getTripById(tripId)?.let { tripDao.deleteTrip(it) }
    }

    suspend fun getTripById(tripId: String): Trip? {
        return tripDao.getTripById(tripId)?.toTrip()
    }


    private val api = RetrofitClient.instance.create(TripApiService::class.java)

    suspend fun fetchTripsFromApi(): List<Trip> {
        val dtoList = api.getTrips()
        return dtoList.map { dto -> dto.toTrip() } // Necessitaràs un mapper
    }




}
