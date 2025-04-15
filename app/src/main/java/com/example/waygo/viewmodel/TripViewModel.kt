package com.example.waygo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waygo.models.Trip
import com.example.waygo.repository.TripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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

    fun updateTrip(trip: Trip) {
        viewModelScope.launch {
            TripRepository.updateTrip(trip)
            loadTrips()
        }
    }

    fun deleteTrip(id: String) {
        viewModelScope.launch {
            TripRepository.deleteTrip(id)
            loadTrips()
        }
    }

    fun getTripById(id: String): Trip? {
        return TripRepository.getTripById(id)
    }
}
