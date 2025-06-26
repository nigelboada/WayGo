package com.example.waygo.data.repository

import android.util.Log
import com.example.waygo.data.remote.api.HotelApiService
import com.example.waygo.data.remote.dto.ReserveRequestDto
import com.example.waygo.utils.assignHotelImage
import com.example.waygo.utils.assignRoomImage
import com.example.waygo.data.remote.mapper.toReservation
import com.example.waygo.data.remote.model.Hotel
import com.example.waygo.data.remote.model.ReserveRequest
import com.example.waygo.data.remote.model.Room
import com.example.waygo.domain.repository.HotelRepository
import com.example.waygo.domain.model.Reservation
import javax.inject.Inject




class HotelRepositoryImpl @Inject constructor(
    private val apiService: HotelApiService
) : HotelRepository {

    override suspend fun getHotels(groupId: String): List<Hotel> {
        val response = apiService.getHotels(groupId)

        return response.map { dto ->
            val hotelName = dto.name ?: "Unknown Hotel"

            Hotel(
                id = dto.id,
                name = hotelName,
                address = dto.address ?: "Unknown Address",
                rating = dto.rating?.toInt() ?: 0,
                imageUrl = assignHotelImage(hotelName),
                rooms = dto.rooms?.map { roomDto ->
                    val type = roomDto.roomType ?: "Unknown"
                    Room(
                        id = roomDto.id,
                        roomType = type,
                        price = roomDto.price ?: 0f,
                        images = listOf(assignRoomImage(type))
                    )
                } ?: emptyList(),
                groupId = groupId
            )
        }
    }

    override suspend fun getAvailability(
        groupId: String,
        startDate: String,
        endDate: String,
        hotelId: String?,
        city: String?
    ): List<Hotel> {
        val response = apiService.getAvailability(
            groupId = groupId,
            startDate = startDate,
            endDate = endDate,
            hotelId = hotelId,
            city = city
        )

        Log.d("HotelRepo", "Availability response: $response")

        Log.d("API_RESPONSE", "Available hotels: ${response.availableHotels}")

        return response.availableHotels?.map { dto ->
            val hotelName = dto.name ?: "Unknown Hotel"

            Hotel(
                id = dto.id,
                name = hotelName,
                address = dto.address ?: "Unknown Address",
                rating = dto.rating?.toInt() ?: 0,
                imageUrl = assignHotelImage(hotelName),
                rooms = dto.rooms?.map { roomDto ->
                    val type = roomDto.roomType ?: "Unknown"
                    Room(
                        id = roomDto.id,
                        roomType = type,
                        price = roomDto.price ?: 0f,
                        images = listOf(assignRoomImage(type))
                    )
                } ?: emptyList(),
                groupId = groupId
            )
        } ?: emptyList()

    }

    override suspend fun reserveRoom(groupId: String, request: ReserveRequest): Boolean {
        val dto = ReserveRequestDto(
            hotelId = request.hotelId,
            roomId = request.roomId,
            guestName = request.guestName,
            guestEmail = request.guestEmail,
            startDate = request.startDate,
            endDate = request.endDate
        )
        val response = apiService.reserveRoom(groupId, dto)
        return response.message.contains("confirmada", ignoreCase = true)
    }

    override suspend fun cancelReservation(groupId: String, request: ReserveRequest): Boolean {
        val dto = ReserveRequestDto(
            hotelId = request.hotelId,
            roomId = request.roomId,
            guestName = request.guestName,
            guestEmail = request.guestEmail,
            startDate = request.startDate,
            endDate = request.endDate
        )
        val response = apiService.cancelReservation(groupId, dto)
        return response.message.contains("cancelada", ignoreCase = true)
    }

    override suspend fun getAllReservations(): Map<String, List<Reservation>> {
        val response = apiService.getAllReservations() // ← això hauria de retornar ResponseBody
        // Decodifica el JSON tu mateixa o via Moshi/Gson
        // Aquí posem un exemple placeholder
        return emptyMap() // <-- implementa-ho com calgui
    }

    override suspend fun cancelById(reservationId: String) {
        apiService.deleteReservationById(reservationId)
    }

    override suspend fun getGroupReservations(groupId: String, guestEmail: String): List<Reservation> {
        val response = apiService.getGroupReservations(groupId, guestEmail)
        return response.reservations.map { dto ->
            dto.toReservation(tripId = groupId)

        }
    }


}
