package com.example.waygo.local.dao

import androidx.room.*
import com.example.waygo.local.entity.TripEntity

@Dao
interface TripDao {

    @Query("SELECT * FROM trips WHERE userId = :userId")
    suspend fun getTripsByUser(userId: String): List<TripEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT) // o REPLACE si vols sobreescriure
    suspend fun insertTrip(trip: TripEntity)

    @Update
    suspend fun updateTrip(trip: TripEntity)

    @Delete
    suspend fun deleteTrip(trip: TripEntity)

    @Query("SELECT * FROM trips WHERE id = :id")
    suspend fun getTripById(id: String): TripEntity?
}
