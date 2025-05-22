package com.example.waygo.data.local.mapper


import com.example.waygo.data.local.entity.ItineraryItemEntity
import com.example.waygo.data.local.entity.TripEntity
import com.example.waygo.data.local.entity.UserEntity
import com.example.waygo.domain.model.ItineraryItem
import com.example.waygo.domain.model.Trip
import com.example.waygo.domain.model.User
import java.util.Date

fun Trip.toEntity(userId: Int): TripEntity =
    TripEntity(
        id = 0, // Room el genera
        userId = userId,
        destination = location,
        startDate = startDate.toLongOrNull() ?: 0L,
        endDate = endDate.toLongOrNull() ?: 0L,
        budget = 0.0, // Perquè Trip no té aquest camp
        notes = description,
        isFavorite = false, // Perquè Trip no el té
        coverImageUrl = "" // Default, o pots afegir-lo a Trip
    )

fun TripEntity.toDomain(itineraryItems: List<ItineraryItem> = emptyList()): Trip =
    Trip(
        id = id.toString(), // Si Trip.id és String
        title = "", // TripEntity no té title
        description = notes,
        location = destination,
        startDate = startDate.toString(),
        endDate = endDate.toString(),
        activities = itineraryItems
    )


fun ItineraryItem.toEntity(tripIdInt: Int): ItineraryItemEntity {
    // Combina 'day' i 'hour' en un objecte Date → timestamp (Long)
    val dateTimeStr = "${this.day} ${this.hour}"
    val formatter = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm")
    val date = formatter.parse(dateTimeStr)?.time ?: 0L

    return ItineraryItemEntity(
        tripId = tripIdInt,
        description = this.description,
        date = date,
        location = this.title // O crea un camp 'title' nou si és diferent
    )
}


fun ItineraryItemEntity.toDomain(tripIdStr: String): ItineraryItem {
    val date = java.util.Date(this.date)
    val dayFormat = java.text.SimpleDateFormat("yyyy-MM-dd")
    val hourFormat = java.text.SimpleDateFormat("HH:mm")

    return ItineraryItem(
        id = this.id.toString(),
        tripId = tripIdStr,
        title = this.location, // O assigna un altre camp si són diferents
        description = this.description,
        day = dayFormat.format(date),
        hour = hourFormat.format(date)
    )
}



fun User.toEntity(): UserEntity =
    UserEntity(
        id = id,
        userId = userId,
        username = username,
        name = name,
        email = email,
        phoneNumber = phoneNumber,
        birthdate = birthdate,
        address = address,
        country = country,
        acceptEmails = acceptEmails,
        profilePictureUrl = profilePictureUrl,
        bio = bio,
        followers = followers,
        following = following,
        totalLikes = totalLikes,
        uploadedRoutes = uploadedRoutes
    )

fun UserEntity.toDomain(): User =
    User(
        id = id,
        userId = userId,
        username = username,
        name = name,
        email = email,
        phoneNumber = phoneNumber,
        birthdate = birthdate,
        address = address,
        country = country,
        acceptEmails = acceptEmails,
        profilePictureUrl = profilePictureUrl,
        bio = bio,
        followers = followers,
        following = following,
        totalLikes = totalLikes,
        uploadedRoutes = uploadedRoutes,
    )