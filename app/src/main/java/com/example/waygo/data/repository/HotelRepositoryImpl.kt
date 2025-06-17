package com.example.waygo.data.repository

import com.example.waygo.data.remote.api.HotelApiService
import com.example.waygo.data.remote.dto.ReserveRequestDto
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
        return apiService.getHotels(groupId).map { dto ->
            Hotel(
                id = dto.id,
                name = dto.name,
                address = dto.address,
                rating = dto.rating,
                imageUrl = dto.imageUrl ?: "",
                rooms = dto.rooms?.map { roomDto ->
                    Room(
                        id = roomDto.id,
                        roomType = roomDto.roomType ?: "Unknown",
                        price = roomDto.price,
                        images = roomDto.images
                    )
                } ?: emptyList()
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

        return response.availableHotels.map { dto ->
            Hotel(
                id = dto.id,
                name = dto.name,
                address = dto.address,
                rating = dto.rating,
                imageUrl = dto.imageUrl ?: "",
                rooms = dto.rooms?.map { roomDto ->
                    Room(
                        id = roomDto.id,
                        roomType = roomDto.roomType ?: "Unknown",
                        price = roomDto.price,
                        images = roomDto.images
                    )
                } ?: emptyList()
            )
        }
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
        return response.reservations.map { it.toReservation() }
    }


}
