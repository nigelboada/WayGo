package com.example.waygo.data.remote.dto

/* ---------- reserva que llega en listados y GET/DELETE ---------- */
data class ReservationDto(
    val id: String,
    val hotelId: String,
    val roomId: String,
    val startDate: String,
    val endDate: String,
    val guestName: String,
    val guestEmail: String,

    /* nuevos campos (pueden ser null si backend los omite en otro endpoint) */
    val hotel: HotelDto,
    val room:  RoomDto
)