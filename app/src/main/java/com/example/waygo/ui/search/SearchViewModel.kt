package com.example.waygo.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waygo.domain.repository.HotelRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val hotelRepository: HotelRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    fun updateCity(city: String) {
        _uiState.value = _uiState.value.copy(selectedCity = city)
    }

    fun updateDates(start: String, end: String) {
        _uiState.value = _uiState.value.copy(startDate = start, endDate = end)
    }

    fun searchHotels() {


        val city = _uiState.value.selectedCity
        val start = _uiState.value.startDate
        val end = _uiState.value.endDate

        Log.d("DEBUG", "searchHotels() cridada amb ciutat=$city, start=$start, end=$end")

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val results = hotelRepository.getAvailability("G05", start, end, null, city)

                val gson = Gson()
                Log.d("API_RESPONSE", "Hotels JSON: ${gson.toJson(results)}")

                Log.d("API_RESPONSE", "Hotels rebuts: ${results.size}")
                results.forEach { hotel ->
                    Log.d("API_RESPONSE", "Hotel: ${hotel.name} (${hotel.id})")
                }
                _uiState.value = _uiState.value.copy(hotels = results, isLoading = false)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error: ${e.message}", e)
                _uiState.value = _uiState.value.copy(error = e.message, isLoading = false)
            }
        }
    }

}
