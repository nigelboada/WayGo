// → app/src/main/java/com/example/waygo/di/RepositoryModule.kt

package com.example.waygo.di

import com.example.waygo.data.repository.HotelRepositoryImpl
import com.example.waygo.data.repository.ReservationRepositoryImpl
import com.example.waygo.data.repository.TaskRepositoryImpl
import com.example.waygo.data.repository.TripRepositoryImpl
import com.example.waygo.domain.repository.HotelRepository
import com.example.waygo.domain.repository.ReservationRepository
import com.example.waygo.domain.repository.TaskRepository
import com.example.waygo.domain.repository.TripRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    @Singleton
    abstract fun bindHotelRepository(
        impl: HotelRepositoryImpl
    ): HotelRepository

    @Binds
    @Singleton
    abstract fun bindReservationRepository(
        impl: ReservationRepositoryImpl
    ): ReservationRepository

    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        impl: TaskRepositoryImpl
    ): TaskRepository

    @Binds
    @Singleton
    abstract fun bindTripRepository(
        impl: TripRepositoryImpl
    ): TripRepository
}
