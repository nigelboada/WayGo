package com.example.waygo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waygo.domain.model.Reservation
import com.example.waygo.domain.repository.ReservationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationsAllViewModel @Inject constructor(
    private val reservationRepo: ReservationRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<Map<String, List<Reservation>>>(emptyMap())
    val uiState: StateFlow<Map<String, List<Reservation>>> = _uiState

    fun load() = viewModelScope.launch {
        _uiState.value = reservationRepo.getAllReservations()
    }

    fun deleteReservation(reservationId: String) = viewModelScope.launch {
        val ok = reservationRepo.deleteReservation(reservationId)
        if (ok) load()         // recarrega posts-borrat
        else { /* mostrar error, snack si vols */ }
    }
}