package com.example.waygo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.waygo.data.local.dao.ReservationDao
import com.example.waygo.data.local.dao.TaskDao
import com.example.waygo.data.local.dao.SubTaskDao
import com.example.waygo.data.local.dao.TripDao
import com.example.waygo.data.local.dao.TripImageDao
import com.example.waygo.data.local.entity.UserEntity
import com.example.waygo.data.local.dao.UserDao
import com.example.waygo.data.local.entity.ReservationEntity
import com.example.waygo.data.local.entity.SubTaskEntity
import com.example.waygo.data.local.entity.TaskEntity
import com.example.waygo.data.local.entity.TripEntity
import com.example.waygo.data.local.entity.TripImageEntity

@Database(

    entities = [

        UserEntity::class,
        TripEntity::class,
        TaskEntity::class,
        SubTaskEntity::class,
        ReservationEntity::class,
        TripImageEntity::class,

               ], version = 9)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun tripDao(): TripDao
    abstract fun taskDao(): TaskDao
    abstract fun subTaskDao(): SubTaskDao
    abstract fun reservationDao(): ReservationDao
    abstract fun tripImageDao(): TripImageDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context, AppDatabase::class.java, "waygo.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }
}
