package com.example.waygo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.waygo.data.local.entity.UserEntity
import com.example.waygo.data.local.dao.AccessLogDao
import com.example.waygo.data.local.dao.ItineraryDao
import com.example.waygo.data.local.dao.TripDao
import com.example.waygo.data.local.dao.UserDao
import com.example.waygo.data.local.entity.AccessLogEntity
import com.example.waygo.data.local.entity.ItineraryItemEntity
import com.example.waygo.data.local.entity.TripEntity

@Database(
    entities = [TripEntity::class, ItineraryItemEntity::class, UserEntity::class, AccessLogEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao
    abstract fun itineraryDao(): ItineraryDao
    abstract fun userDao(): UserDao
    abstract fun accessLogDao(): AccessLogDao
}



