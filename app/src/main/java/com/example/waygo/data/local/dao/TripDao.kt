package com.example.waygo.data.local.dao

import androidx.room.*
import com.example.waygo.data.local.entity.TripEntity


@Dao
interface TripDao {

    @Query("SELECT * FROM trips")
    suspend fun getTrips(): List<TripEntity>

    @Query("SELECT * FROM trips WHERE userId = :userId")
    suspend fun getTripsByUser(userId: Int): List<TripEntity>

    @Insert
    suspend fun insertTrip(trip: TripEntity): Long

    @Update
    suspend fun updateTrip(trip: TripEntity): Int

    @Query("DELETE FROM trips WHERE id = :tripId")
    suspend fun deleteTrip(tripId: Int)

    @Query("SELECT * FROM trips WHERE id = :tripId")
    suspend fun getTripById(tripId: Int): TripEntity?
}