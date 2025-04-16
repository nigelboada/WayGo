package com.example.waygo.viewmodel

import androidx.lifecycle.ViewModel
import com.example.waygo.models.ItineraryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class ActivityViewModel : ViewModel() {
    private val _activities = MutableStateFlow<List<ItineraryItem>>(emptyList())
    val activities = _activities.asStateFlow()

    fun addActivity(activity: ItineraryItem) {
        _activities.value += activity
    }

    fun updateActivity(updated: ItineraryItem) {
        _activities.value = _activities.value.map {
            if (it.id == updated.id) updated else it
        }
    }

    fun deleteActivity(id: String) {
        _activities.value = _activities.value.filter { it.id != id }
    }

    fun getActivityById(activityId: String): ItineraryItem? {
        return _activities.value.find { it.id == activityId }
    }

}
