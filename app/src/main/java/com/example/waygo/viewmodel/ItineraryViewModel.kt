package com.example.waygo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waygo.models.ItineraryItem
import com.example.waygo.repository.TripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItineraryViewModel(private val tripId: String) : ViewModel() {

    private val _itinerary = MutableStateFlow<List<ItineraryItem>>(emptyList())
    val itinerary: StateFlow<List<ItineraryItem>> = _itinerary

    init {
        loadItinerary()
    }

    private fun loadItinerary() {
        TripRepository.getTripById(tripId)?.let {
            _itinerary.value = it.itinerary
        }
    }

    fun addActivity(activity: ItineraryItem) {
        viewModelScope.launch {
            TripRepository.addActivityToTrip(tripId, activity)
            loadItinerary()
        }
    }

    fun updateActivity(activity: ItineraryItem) {
        viewModelScope.launch {
            TripRepository.updateActivityInTrip(tripId, activity)
            loadItinerary()
        }
    }

    fun deleteActivity(activityId: String) {
        viewModelScope.launch {
            TripRepository.deleteActivityFromTrip(tripId, activityId)
            loadItinerary()
        }
    }

    fun getActivityById(activityId: String): ItineraryItem? {
        return _itinerary.value.find { it.id == activityId }
    }

}
