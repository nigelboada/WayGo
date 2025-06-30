package com.example.waygo.data.repository

import com.example.waygo.data.local.dao.TripDao
import com.example.waygo.data.local.dao.TripImageDao
import com.example.waygo.data.local.entity.TripImageEntity
import com.example.waygo.data.local.mapper.toTrip
import com.example.waygo.data.local.mapper.toTripEntity
import com.example.waygo.domain.model.Trip
import com.example.waygo.domain.repository.TripRepository
import javax.inject.Inject

class TripRepositoryImpl @Inject constructor(
    private val tripDao: TripDao,
    private val dao: TripImageDao,
    private val tripImageDao: TripImageDao
) : TripRepository {

    override suspend fun getAllTripsForUser(userId: String): List<Trip> =
        tripDao.getTripsByUser(userId).map { it.toTrip() }

    override suspend fun addTrip(trip: Trip, userId: String) {
        tripDao.insertTrip(trip.toTripEntity(userId))
    }

    override suspend fun updateTrip(trip: Trip, userId: String) {
        tripDao.updateTrip(trip.toTripEntity(userId))
    }

    override suspend fun deleteTrip(tripId: String) {
        tripDao.getTripById(tripId)?.let { tripDao.deleteTrip(it) }
    }

    override suspend fun getTripById(tripId: String): Trip? =
        tripDao.getTripById(tripId)?.toTrip()

    // Aquí resolgim la part de les imatges:
    override suspend fun addImagesToTrip(tripId: String, imageUris: List<String>) {
        val entities = imageUris.map { uri ->
            TripImageEntity(
                tripId = tripId,
                imageUri = uri
            )
        }
        tripImageDao.insertAll(entities)
    }

    override suspend fun getImagesForTrip(tripId: String): List<String> =
        tripImageDao
            .getImagesForTrip(tripId)
            .map { it.imageUri }    // <-- aquí agafes la propietat correcta


    override suspend fun deleteImageFromTrip(tripId: String, uri: String) {
        dao.deleteImage(tripId, uri)
    }



}
