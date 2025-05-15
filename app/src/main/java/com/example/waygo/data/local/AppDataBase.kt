package com.example.waygo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.waygo.data.local.dao.TripDao
import com.example.waygo.data.local.entity.UserEntity
import com.example.waygo.data.local.dao.UserDao
import com.example.waygo.data.local.entity.TripEntity

@Database(entities = [UserEntity::class, TripEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun tripDao(): TripDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "your_database_name").fallbackToDestructiveMigration().build().also { INSTANCE = it }
            }
        }
    }
}
