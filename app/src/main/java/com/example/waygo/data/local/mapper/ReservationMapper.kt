// → app/src/main/java/com/example/waygo/data/local/mapper/ReservationMapper.kt

package com.example.waygo.data.local.mapper

import com.example.waygo.data.local.entity.ReservationEntity
import com.example.waygo.domain.model.Reservation
import javax.inject.Inject

class ReservationMapper @Inject constructor() {

    fun toEntity(r: Reservation): ReservationEntity = ReservationEntity(
        id         = r.id,
        tripId     = r.tripId,
        hotelId    = r.hotelId,
        hotelName  = r.hotelName,
        roomId     = r.roomId,
        roomType   = r.roomType,
        price      = r.price,
        startDate  = r.startDate,
        endDate    = r.endDate,
        guestEmail = r.guestEmail,
        imageUrl   = r.imageUrl
    )

    fun toDomain(e: ReservationEntity): Reservation = Reservation(
        id         = e.id,
        tripId     = e.tripId,
        hotelId    = e.hotelId,
        hotelName  = e.hotelName,
        roomId     = e.roomId,
        roomType   = e.roomType,
        price      = e.price,
        startDate  = e.startDate,
        endDate    = e.endDate,
        guestEmail = e.guestEmail,
        imageUrl   = e.imageUrl
    )
}
