package com.example.waygo.data.repository

import android.util.Log
import com.example.waygo.data.local.dao.ItineraryDao
import com.example.waygo.data.local.mapper.toDomain
import com.example.waygo.data.local.mapper.toEntity
import com.example.waygo.domain.model.ItineraryItem
import com.example.waygo.domain.repository.ItineraryRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItineraryRepositoryImpl @Inject constructor(
    private val itineraryDao: ItineraryDao
) : ItineraryRepository {

    private val TAG = "ItineraryRepository"

    override suspend fun addItineraryItem(item: ItineraryItem): Boolean {
        Log.d(TAG, "Añadiendo itinerario para el viaje con ID: ${item.tripId}")
        val tripIdInt = item.tripId.toIntOrNull() ?: return false
        val id = itineraryDao.addItineraryItem(item.toEntity(tripIdInt))
        return id > 0
    }

    override suspend fun getItineraryItems(tripId: Int): List<ItineraryItem> {
        Log.d(TAG, "Obteniendo itinerarios para el viaje con ID: $tripId")
        return itineraryDao.getItineraryForTrip(tripId).map { it.toDomain(tripId.toString()) }
    }

    override suspend fun getItineraryItemById(id: Int): ItineraryItem? {
        Log.d(TAG, "Buscando itinerario con ID: $id")
        return itineraryDao.getItineraryForTrip(id).find { it.id == id }?.toDomain("tripIdDesconegut")
    }

    override suspend fun updateItineraryItem(item: ItineraryItem): Boolean {
        Log.d(TAG, "Actualizando itinerario con ID: ${item.id}")
        val tripIdInt = item.tripId.toIntOrNull() ?: return false
        return itineraryDao.updateItineraryItem(item.toEntity(tripIdInt)) > 0
    }

    override suspend fun deleteItineraryItem(id: Int): Boolean {
        Log.d(TAG, "Eliminando itinerario con ID: $id")
        itineraryDao.deleteItineraryItem(id)
        return true
    }
}
