package com.example.waygo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waygo.models.Hotel
import com.example.waygo.repository.HotelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HotelViewModel(private val repository: HotelRepository) : ViewModel() {

    private val _hotels = MutableStateFlow<List<Hotel>>(emptyList())
    val hotels: StateFlow<List<Hotel>> = _hotels

    fun fetchHotels() {
        viewModelScope.launch {
            val result = repository.getHotels()
            _hotels.value = result
        }
    }
}
