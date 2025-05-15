package com.example.waygo.di.dto

import com.example.waygo.models.Trip

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
