package com.example.waygo.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waygo.domain.repository.HotelRepository
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

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val results = hotelRepository.getAvailability("G05", start, end, null, city)
                _uiState.value = _uiState.value.copy(hotels = results, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message, isLoading = false)
            }
        }
    }
}
