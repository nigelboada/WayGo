package com.example.waygo.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waygo.models.Trip
import com.example.waygo.repository.TripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TripViewModel : ViewModel() {

    private val _trips = MutableStateFlow<List<Trip>>(emptyList())
    val trips: StateFlow<List<Trip>> = _trips

    init {
        loadTrips()
    }

    private fun loadTrips() {
        _trips.value = TripRepository.getAllTrips()
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
            TripRepository.addTrip(newTrip)
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
            TripRepository.updateTrip(updatedTrip)
            loadTrips()  // O la funció que utilitzes per recarregar els viatges
        }
    }


    fun deleteTrip(id: String) {
        viewModelScope.launch {
            TripRepository.deleteTrip(id)
            loadTrips()
        }
    }

    private val _trip = MutableStateFlow<Trip?>(null)
    val trip: StateFlow<Trip?> = _trip

    fun getTripById(tripId: String) {
        viewModelScope.launch {

            _trip.value = TripRepository.getTripById(tripId)
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