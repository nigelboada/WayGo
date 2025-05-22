package com.example.waygo.di


import HotelRepositoryImpl
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.waygo.BuildConfig
import com.example.waygo.data.local.AppDatabase
import com.example.waygo.data.local.dao.AccessLogDao
import com.example.waygo.data.local.dao.ItineraryDao
import com.example.waygo.data.local.dao.TripDao
import com.example.waygo.data.local.dao.UserDao
import com.example.waygo.data.remote.api.HotelApiService
import com.example.waygo.data.repository.AccessLogRepositoryImpl
import com.example.waygo.data.repository.HotelRepositoryImpl
import com.example.waygo.domain.repository.ItineraryRepository
import com.example.waygo.data.repository.ItineraryRepositoryImpl
import com.example.waygo.domain.repository.TripRepository
import com.example.waygo.data.repository.TripRepositoryImpl
import com.example.waygo.data.repository.UserRepositoryImpl
import com.example.waygo.domain.repository.AccessLogRepository
import com.example.waygo.domain.repository.HotelRepository
import com.example.waygo.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import com.example.waygo.ui.viewmodel.RegisterViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences =
        context.getSharedPreferences("${BuildConfig.APPLICATION_ID}_preferences", Context.MODE_PRIVATE)

    // context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE) //bad implementation

    @Provides
    @Singleton
    fun provideFormValidationViewModel(
        @ApplicationContext context: Context
    ): RegisterViewModel = RegisterViewModel(context)

    @Provides
    @Singleton
    fun provideTripRepository(tripDao: TripDao, itineraryDao: ItineraryDao): TripRepository {
        return TripRepositoryImpl(tripDao, itineraryDao)
    }

    @Provides
    @Singleton
    fun provideItineraryRepository(itineraryDao: ItineraryDao): ItineraryRepository {
        return ItineraryRepositoryImpl(itineraryDao)
    }

    @Provides
    fun provideTripDao(db: AppDatabase): TripDao {
        return db.tripDao()
    }

    @Provides
    fun provideItineraryDao(db: AppDatabase): ItineraryDao {
        return db.itineraryDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "waygo_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(userDao)
    }

    @Provides
    @Singleton
    fun provideAccessLogRepository(accessLogDao: AccessLogDao): AccessLogRepository {
        return AccessLogRepositoryImpl(accessLogDao)
    }

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao {
        return db.userDao()
    }

    @Provides
    fun provideAccessLogDao(db: AppDatabase): AccessLogDao {
        return db.accessLogDao()
    }

    /* --- Repository --- */
    @Provides
    @Singleton
    fun provideHotelRepository(api: HotelApiService, taskDao: TripDao): HotelRepository =
        HotelRepositoryImpl(api, taskDao)

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideHotelApi(
        okHttpClient: OkHttpClient
    ): HotelApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.HOTELS_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HotelApiService::class.java)
    }
}
