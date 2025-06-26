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
class ReservationDetailViewModel @Inject constructor(
    private val reservationRepo: ReservationRepository
) : ViewModel() {

    private val _reservation = MutableStateFlow<Reservation?>(null)
    val reservation: StateFlow<Reservation?> = _reservation

    private val _hotelImage = MutableStateFlow<String?>(null)
    val hotelImage: StateFlow<String?> = _hotelImage

    private val _roomImage = MutableStateFlow<String?>(null)
    val roomImage: StateFlow<String?> = _roomImage

    fun load(groupId: String, tripId: String, reservationId: String) {
        viewModelScope.launch {
            // 1) Recupera la reserva
            val list = reservationRepo.getReservationsForTrip(tripId)
            val res = list.find { it.id == reservationId }
            _reservation.value = res

            res?.let {
                // 2) Assigna la imatge de l’habitació
                _roomImage.value = it.roomImageUrl
                // 3) Assigna la imatge de l’hotel
                _hotelImage.value = it.hotelImageUrl
            }
        }
    }
}
