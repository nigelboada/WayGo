package com.example.waygo.di

import android.content.Context
import android.content.SharedPreferences
import com.example.waygo.data.local.AppDatabase
import com.example.waygo.data.remote.TripApiService
import com.example.waygo.data.remote.api.HotelApiService
import com.example.waygo.domain.repository.ReservationRepository
import com.example.waygo.domain.repository.TripRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

import com.example.waygo.data.repository.ReservationRepositoryImpl
import com.example.waygo.data.local.mapper.ReservationMapper


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "http://13.38.52.243/"

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }


    @Provides
    @Singleton
    fun provideTripRepository(
        @ApplicationContext context: Context,
        tripApi: TripApiService
    ): TripRepository {
        val db = AppDatabase.getDatabase(context)
        return TripRepository(db.tripDao(), tripApi)
    }



    @Singleton
    @Provides
    fun provideTripApiService(retrofit: Retrofit): TripApiService {
        return retrofit.create(TripApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideHotelApiService(retrofit: Retrofit): HotelApiService =
        retrofit.create(HotelApiService::class.java)

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("WayGoPrefs", Context.MODE_PRIVATE)



}
