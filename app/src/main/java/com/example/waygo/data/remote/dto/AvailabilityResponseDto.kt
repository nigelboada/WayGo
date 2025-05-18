package com.example.waygo.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AvailabilityResponseDto(
    @SerializedName("available_hotels")
    val availableHotels: List<HotelDto>
)
