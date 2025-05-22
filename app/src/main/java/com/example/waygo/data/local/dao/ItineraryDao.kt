package com.example.waygo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.waygo.data.local.entity.ItineraryItemEntity


@Dao
interface ItineraryDao {
    @Query("SELECT * FROM itineraries WHERE tripId = :tripId")
    suspend fun getItineraryForTrip(tripId: Int): List<ItineraryItemEntity>

    @Insert
    suspend fun addItineraryItem(itineraryItem: ItineraryItemEntity): Long

    @Query("DELETE FROM itineraries WHERE id = :itineraryItemId")
    suspend fun deleteItineraryItem(itineraryItemId: Int)

    @Update
    suspend fun updateItineraryItem(itineraryItem: ItineraryItemEntity): Int
}