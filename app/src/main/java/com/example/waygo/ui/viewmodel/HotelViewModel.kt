package com.example.waygo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waygo.domain.model.Hotel
import com.example.waygo.domain.repository.HotelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.waygo.BuildConfig


class HotelViewModel @Inject constructor(private val repository: HotelRepository) : ViewModel() {

    private val _hotels = MutableStateFlow<List<Hotel>>(emptyList())
    val hotels: StateFlow<List<Hotel>> = _hotels

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading



    init {
        loadHotels()
    }

    fun loadHotels() {
        viewModelScope.launch {
            _isLoading.value = true
            val groupId = BuildConfig.GROUP_ID
            val result = repository.getHotels(groupId)
            result.let {
                _hotels.value = it
            }
            _isLoading.value = false
        }
    }
}

