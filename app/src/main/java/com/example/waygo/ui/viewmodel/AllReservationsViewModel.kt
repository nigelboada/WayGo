package com.example.waygo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waygo.data.local.entity.ReservationEntity
import com.example.waygo.domain.model.Trip
import com.example.waygo.domain.repository.ReservationRepository
import com.example.waygo.domain.repository.TripRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllReservationsViewModel @Inject constructor(
    private val reservationRepo: ReservationRepository,
    private val tripRepo: TripRepository
) : ViewModel() {
    private val _items = MutableStateFlow<List<Pair<ReservationEntity, Trip?>>>(emptyList())
    val items: StateFlow<List<Pair<ReservationEntity, Trip?>>> = _items

    private val userId: String
        get() = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()

    fun loadAll() = viewModelScope.launch {
        val reses = reservationRepo.getAllReservationsForUser(userId)
        val withTrips = reses.map { res ->
            val trip = tripRepo.getTripById(res.tripId)
            res to trip
        }
        _items.value = withTrips
    }

    /**  Nou: esborra una reserva i recarrega la llista  */
    fun deleteReservation(reservationId: String) = viewModelScope.launch {
        val success = reservationRepo.deleteReservation(reservationId)
        if (success) {
            loadAll()
        } else {
            // aquí podries emetre un event de Snackbar si cal
        }
    }
}
