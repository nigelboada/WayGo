package com.example.waygo.viewmodel

import androidx.lifecycle.ViewModel
import com.example.waygo.models.Activity
import com.example.waygo.repository.ActivityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class ActivityViewModel : ViewModel() {
    private val _activities = MutableStateFlow<List<Activity>>(emptyList())
    val activities = _activities.asStateFlow()

    fun addActivity(activity: Activity) {
        _activities.value += activity
    }


    fun updateActivity(updated: Activity) {
        _activities.value = _activities.value.map {
            if (it.id == updated.id) updated else it
        }
    }

    fun deleteActivity(id: String) {
        _activities.value = _activities.value.filter { it.id != id }
    }

//    fun getActivityById(id: String): Activity? {
//        return ActivityRepository.getActivityById(id) // Suposant que tens un repositori que retorna un StateFlow
//    }

}
