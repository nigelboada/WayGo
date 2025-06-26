package com.example.waygo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.waygo.data.local.entity.ReservationEntity

@Dao
interface ReservationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reservation: ReservationEntity)

    @Query("SELECT * FROM reservations WHERE tripId = :tripId")
    suspend fun getReservationsForTrip(tripId: String): List<ReservationEntity>

    @Query("SELECT * FROM reservations")
    suspend fun getAllReservations(): List<ReservationEntity>

    @Query("DELETE FROM reservations WHERE id = :id")
    suspend fun deleteById(id: String)


}
