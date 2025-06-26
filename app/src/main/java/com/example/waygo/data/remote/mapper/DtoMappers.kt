package com.example.waygo.data.remote.mapper

import com.example.waygo.data.remote.dto.HotelDto
import com.example.waygo.data.remote.dto.ReservationDto
import com.example.waygo.data.remote.dto.ReserveRequestDto
import com.example.waygo.data.remote.dto.RoomDto
import com.example.waygo.data.remote.dto.TripDto
import com.example.waygo.data.remote.model.Hotel
import com.example.waygo.domain.model.Reservation
import com.example.waygo.data.remote.model.ReserveRequest
import com.example.waygo.data.remote.model.Room
import com.example.waygo.domain.model.Trip

fun HotelDto.toDomain(): Hotel = Hotel(
    id        = id,
    name      = name ?: "Unknown Hotel",
    address   = address ?: "Unknown Address",
    rating    = rating ?: 0,
    imageUrl  = imageUrl ?: "",
    rooms     = rooms
        ?.map { it.toDomain() }      // si no es null lo mapea
        ?: emptyList(),
    groupId = "unknown"// si es null lista vacía
)

fun RoomDto.toDomain(): Room = Room(
    id = id,
    roomType = roomType ?: "Unknown",
    price = price ?: 0f,
    images = images ?: emptyList()
)

fun ReservationDto.toReservation(tripId: String): Reservation = Reservation(
    id         = id,
    tripId     = tripId,
    hotelId    = hotelId,
    hotelName  = hotel?.name ?: "Unknown Hotel",          // extreiem de hotelDto
    roomId     = roomId,
    roomType   = room?.roomType ?: "Unknown",              // extreiem de roomDto
    price      = room?.price ?: 0f,                        // idem
    startDate  = startDate,
    endDate    = endDate,
    guestEmail = guestEmail,
    imageUrl   = hotel?.imageUrl ?: ""                     // agafem la imatge principal de l’hotel
)

fun ReserveRequest.toDto(): ReserveRequestDto = ReserveRequestDto(
    hotelId = hotelId,
    roomId = roomId,
    startDate = startDate,
    endDate = endDate,
    guestName = guestName,
    guestEmail = guestEmail
)


fun TripDto.toTrip(): Trip {
    return Trip(
        id = id,
        title = title,
        description = description,
        location = location,
        startDate = startDate,
        endDate = endDate
    )
}

fun Trip.toTripDto(): TripDto {
    return TripDto(
        id = id,
        title = title,
        description = description,
        location = location,
        startDate = startDate,
        endDate = endDate
    )
}