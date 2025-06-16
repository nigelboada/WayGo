package com.example.waygo.data.remote

import com.example.waygo.data.remote.model.Hotel
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TripApiServiceTest {

    private lateinit var apiService: TripApiService

    @Before
    fun setup() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://13.38.52.243/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()

        apiService = retrofit.create(TripApiService::class.java)
    }

    @Test
    fun testCheckAvailability_returnsHotels() = runBlocking {
        // 👉 valors que han d'existir perquè l'API respongui correctament
        val groupId = "G05" // el groupId
        val startDate = "2024-08-01"
        val endDate = "2024-08-05"

        val hotels: List<Hotel> = apiService.checkAvailability(
            groupId = groupId,
            startDate = startDate,
            endDate = endDate
        )

        assertTrue(hotels.isNotEmpty())
    }
}
