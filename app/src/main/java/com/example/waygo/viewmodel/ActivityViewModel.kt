package com.example.waygo.viewmodel

import androidx.lifecycle.ViewModel
import com.example.waygo.models.Activity
import com.example.waygo.repository.ActivityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ActivityViewModel : ViewModel() {
    private val _activities = MutableStateFlow(ActivityRepository.getAllActivities())
    val activities = _activities.asStateFlow()

    var tripViewModel: TripViewModel? = null

    fun addActivity(activity: Activity) {
        ActivityRepository.addItem(activity)
        _activities.value = ActivityRepository.getAllActivities()
        tripViewModel?.loadTrips()
    }

    fun updateActivity(updated: Activity) {
        ActivityRepository.updateItem(updated)
        _activities.value = ActivityRepository.getAllActivities()
        tripViewModel?.loadTrips()
    }

    fun deleteActivity(id: String) {
        ActivityRepository.deleteItem(id)
        _activities.value = ActivityRepository.getAllActivities()
        tripViewModel?.loadTrips()
    }

    fun getActivityById(id: String): Activity? {
        return ActivityRepository.getActivityById(id)
    }
}