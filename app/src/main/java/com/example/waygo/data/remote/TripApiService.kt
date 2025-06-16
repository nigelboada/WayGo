package com.example.waygo.data.remote

import com.example.waygo.data.remote.model.Hotel
import com.example.waygo.data.remote.model.ReserveRequest
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TripApiService {

    @GET("hotels/{group_id}/hotels")
    suspend fun getHotels(
        @Path("group_id") groupId: String
    ): List<Hotel>

    @GET("hotels/{group_id}/availability")
    suspend fun checkAvailability(
        @Path("group_id") groupId: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("hotel_id") hotelId: String? = null,
        @Query("city") city: String? = null
    ): ResponseBody // Podem canviar-ho si sabem el model concret

    @POST("hotels/{group_id}/reserve")
    suspend fun reserveRoom(
        @Path("group_id") groupId: String,
        @Body request: ReserveRequest
    ): ResponseBody

    @POST("hotels/{group_id}/cancel")
    suspend fun cancelReservation(
        @Path("group_id") groupId: String,
        @Body request: ReserveRequest
    ): ResponseBody

    @GET("hotels/{group_id}/reservations")
    suspend fun getReservations(
        @Path("group_id") groupId: String,
        @Query("guest_email") guestEmail: String? = null
    ): ResponseBody

    @GET("reservations")
    suspend fun getAllReservations(): ResponseBody

    @GET("reservations/{res_id}")
    suspend fun getReservationById(
        @Path("res_id") reservationId: String
    ): ResponseBody

    @DELETE("reservations/{res_id}")
    suspend fun cancelReservationById(
        @Path("res_id") reservationId: String
    ): ResponseBody
}
