package com.example.waygo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waygo.local.entity.TripEntity
import com.example.waygo.models.Itinerary
import com.example.waygo.models.Trip
import com.example.waygo.repository.ActivityRepository
import com.example.waygo.repository.TripRepository
import com.example.waygo.utils.SessionManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TripViewModel(private val tripRepository: TripRepository) : ViewModel() {

    private val _activities = MutableStateFlow<List<Itinerary>>(emptyList())
    val activities: StateFlow<List<Itinerary>> = _activities



    private val _trips = MutableStateFlow<List<Trip>>(emptyList())
    val trips: StateFlow<List<Trip>> = _trips

    private val userId: String
        get() = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()

    init {
        loadTrips()
    }

    internal fun loadTrips() {
        viewModelScope.launch {
            val trips = tripRepository.getAllTripsForUser(userId)
            val tripsWithActivities = trips.map { trip ->
                val activities = ActivityRepository.getItemsForTrip(trip.id)
                trip.copy(activities = activities)
            }
            _trips.value = tripsWithActivities
        }
    }

    fun addTrip(title: String, description: String, startDate: String, endDate: String, location: String) {
        val newTrip = Trip(
            title = title,
            description = description,
            startDate = startDate,
            endDate = endDate,
            location = location
        )
        viewModelScope.launch {
            tripRepository.addTrip(newTrip, userId)
            loadTrips()
        }
    }

    fun updateTrip(tripId: String, title: String, description: String, location: String, startDate: String, endDate: String) {
        val updatedTrip = Trip(
            id = tripId,
            title = title,
            description = description,
            location = location,
            startDate = startDate,
            endDate = endDate
        )

        viewModelScope.launch {
            tripRepository.updateTrip(updatedTrip, userId)
            loadTrips()
        }
    }

    fun deleteTrip(trip: Trip) {
        viewModelScope.launch {
            tripRepository.deleteTrip(trip.id) // Passa directament l'ID com a String
            loadTrips() // opcional, per refrescar la llista
        }
    }



    private val _trip = MutableStateFlow<Trip?>(null)
    val trip: StateFlow<Trip?> = _trip

    fun getTripById(id: String) {
        viewModelScope.launch {
            val trip = tripRepository.getTripById(id) // ara usa l’`id` com a `String`
            _trip.value = trip
        }
    }


    fun getDaysForTrip(tripId: String): List<String> {
        val trip = trips.value.find { it.id == tripId } ?: return emptyList()

        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val start = LocalDate.parse(trip.startDate, formatter)
        val end = LocalDate.parse(trip.endDate, formatter)

        val days = mutableListOf<String>()
        var current = start
        while (!current.isAfter(end)) {
            days.add(current.format(formatter))
            current = current.plusDays(1)
        }
        return days
    }
}
