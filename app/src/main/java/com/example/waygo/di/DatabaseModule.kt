package com.example.waygo.di

import android.content.Context
import androidx.room.Room
import com.example.waygo.data.local.AppDatabase
import com.example.waygo.data.local.dao.ReservationDao
import com.example.waygo.data.local.dao.SubTaskDao
import com.example.waygo.data.local.dao.TaskDao
import com.example.waygo.data.local.dao.TripDao
import com.example.waygo.data.local.dao.TripImageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "waygo.db"
        )
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    fun provideReservationDao(db: AppDatabase): ReservationDao =
        db.reservationDao()

    @Provides
    fun provideTripDao(db: AppDatabase): TripDao =
        db.tripDao()

    @Provides
    fun provideTaskDao(db: AppDatabase): TaskDao =
        db.taskDao()

    @Provides
    fun provideSubTaskDao(db: AppDatabase): SubTaskDao =
        db.subTaskDao()

    @Provides
    fun provideTripImageDao(db: AppDatabase): TripImageDao =
        db.tripImageDao()

}
