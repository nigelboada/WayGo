package com.example.waygo.data.remote.mapper

import com.example.waygo.data.remote.dto.HotelDto
import com.example.waygo.data.remote.dto.ReservationDto
import com.example.waygo.data.remote.dto.ReserveRequestDto
import com.example.waygo.data.remote.dto.RoomDto
import com.example.waygo.data.remote.dto.TripDto
import com.example.waygo.domain.model.Hotel
import com.example.waygo.domain.model.Reservation
import com.example.waygo.domain.model.ReserveRequest
import com.example.waygo.domain.model.Room
import com.example.waygo.domain.model.Trip


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

fun HotelDto.toDomain(): Hotel = Hotel(
    id        = id,
    name      = name,
    address   = address,
    rating    = rating,
    imageUrl  = imageUrl,
    rooms     = rooms
        ?.map { it.toDomain() }      // si no es null lo mapea
        ?: emptyList()               // si es null lista vacía
)

fun RoomDto.toDomain(): Room = Room(
    id       = id,
    roomType = roomType,
    price    = price,
    images   = images
)

fun ReservationDto.toDomain(): Reservation = Reservation(
    id         = id,
    hotelId    = hotelId,
    roomId     = roomId,
    startDate  = startDate,
    endDate    = endDate,
    guestName  = guestName,
    guestEmail = guestEmail,
    hotel = hotel.toDomain(),   // HotelDto → Hotel
    room  = room.toDomain()     // RoomDto  → Room
)

fun ReserveRequest.toDto(): ReserveRequestDto = ReserveRequestDto(
    hotelId = hotelId,
    roomId = roomId,
    startDate = startDate,
    endDate = endDate,
    guestName = guestName,
    guestEmail = guestEmail
)