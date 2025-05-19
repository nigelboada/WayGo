package com.example.waygo.data.remote.api

import com.example.waygo.data.remote.dto.AvailabilityResponseDto
import com.example.waygo.data.remote.dto.HotelDto
import com.example.waygo.data.remote.dto.ReservationDto
import com.example.waygo.data.remote.dto.ReservationResponseDto
import com.example.waygo.data.remote.dto.ReserveRequestDto
import com.example.waygo.domain.model.Hotel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface HotelApiService {

    /* ------------ Hotels & Availability ------------ */

    @GET("hotels/{group_id}/hotels")
    suspend fun getHotels(
        @Path("group_id") groupId: String
    ): List<HotelDto>

    @GET("hotels/{group_id}/availability")
    suspend fun getAvailability(
        @Path("group_id") groupId: String,
        @Query("start_date") startDate: String,
        @Query("end_date")   endDate: String,
        @Query("hotel_id")   hotelId: String? = null,
        @Query("city")   city: String? = null
    ): AvailabilityResponseDto

    /* ------------ Reservations by group ------------ */

    @POST("hotels/{group_id}/reserve")
    suspend fun reserveRoom(
        @Path("group_id") groupId: String,
        @Body             request: ReserveRequestDto
    ): ReservationResponseDto

    @POST("hotels/{group_id}/cancel")
    suspend fun cancelReservation(
        @Path("group_id") groupId: String,
        @Body             request: CancelRequestDto            // same fields as Reserve
    ): ApiMessageDto                                           // e.g. { "message": "Reserva cancelada" }

    @GET("hotels/{group_id}/reservations")
    suspend fun getGroupReservations(
        @Path("group_id") groupId: String,
        @Query("guest_email") guestEmail: String? = null
    ): ReservationsWrapperDto                                  // { reservations:[...] }

    /* ------------ Admin-level (all groups) ------------ */

    @GET("reservations")
    suspend fun getAllReservations(): AllReservationsDto       // { groups:{ G01:[...], G02:[...] } }

    @GET("reservations/{res_id}")
    suspend fun getReservationById(
        @Path("res_id") resId: String
    ): ReservationDto

    @DELETE("reservations/{res_id}")
    suspend fun deleteReservationById(
        @Path("res_id") resId: String
    ): ReservationDto                                          // returns the deleted object
}

/* Cancel uses the same body as Reserve */
typealias CancelRequestDto = ReserveRequestDto

/* Generic message wrapper */
data class ApiMessageDto(val message: String)

/* Wrapper used by  GET /hotels/{group}/reservations */
data class ReservationsWrapperDto(
    val reservations: List<ReservationDto>
)

/* Wrapper used by  GET /reservations */
data class AllReservationsDto(
    val groups: Map<String, List<ReservationDto>>
)



