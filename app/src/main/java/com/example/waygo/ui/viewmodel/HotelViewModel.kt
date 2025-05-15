package com.example.waygo.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waygo.di.HotelApiService
import com.example.waygo.di.RetrofitClient
import com.example.waygo.di.dto.toHotel
import com.example.waygo.domain.model.Hotel
import com.example.waygo.domain.repository.HotelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HotelViewModel(private val repository: HotelRepository) : ViewModel() {

    var hotels = mutableStateListOf<Hotel>()
        private set

    fun fetchHotels() {
        viewModelScope.launch {
            try {
                val hotelsFromApi = repository.getHotels()  // Això ja fa la crida i el mapping
                hotels.clear()
                hotels.addAll(hotelsFromApi)
            } catch (e: Exception) {
                Log.e("HotelViewModel", "Error carregant hotels: ${e.message}")
            }
        }
    }
}
