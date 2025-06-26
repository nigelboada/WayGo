package com.example.waygo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.waygo.data.local.entity.TripImageEntity

@Dao
interface TripImageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(images: List<TripImageEntity>)

    @Query("SELECT * FROM trip_images WHERE tripId = :tripId")
    suspend fun getImagesForTrip(tripId: String): List<TripImageEntity>
}
