package com.example.waygo.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.waygo.domain.model.Itinerary
import com.example.waygo.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ActivityViewModel : ViewModel() {
    private val _activities = MutableStateFlow(ActivityRepository.getAllActivities())
    val activities = _activities.asStateFlow()

    var tripViewModel: TripViewModel? = null

    fun addActivity(itinerary: Itinerary) {
        ActivityRepository.addItem(itinerary)
        _activities.value = ActivityRepository.getAllActivities()
        tripViewModel?.loadTrips()
    }

    fun updateActivity(updated: Itinerary) {
        ActivityRepository.updateItem(updated)
        _activities.value = ActivityRepository.getAllActivities()
        tripViewModel?.loadTrips()
    }

    fun deleteActivity(id: String) {
        ActivityRepository.deleteItem(id)
        _activities.value = ActivityRepository.getAllActivities()
        tripViewModel?.loadTrips()
    }

    fun getActivityById(id: String): Itinerary? {
        return ActivityRepository.getActivityById(id)
    }
}