package com.example.waygo.ui.search

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waygo.domain.model.Reservation
import com.example.waygo.data.remote.model.Hotel
import com.example.waygo.data.remote.model.Room
import com.example.waygo.domain.repository.HotelRepository
import com.example.waygo.domain.repository.ReservationRepository
import com.example.waygo.domain.repository.TripRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val hotelRepository: HotelRepository,
    private val reservationRepo: ReservationRepository,
    private val tripRepo: TripRepository
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



    fun reserveRoom(hotel: Hotel, room: Room) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        viewModelScope.launch {
            val trips = tripRepo.getAllTripsForUser(userId)
            val trip = trips.firstOrNull()

            if (trip == null) {
                _uiState.value = _uiState.value.copy(error = "Crea un viatge abans de reservar")
                return@launch
            }

            val reservation = Reservation(
                id         = UUID.randomUUID().toString(),
                tripId     = trip.id,
                hotelId    = hotel.id,
                hotelName  = hotel.name,
                roomId     = room.id,
                roomType   = room.roomType,
                price      = room.price,
                startDate  = _uiState.value.startDate,
                endDate    = _uiState.value.endDate,
                guestEmail = FirebaseAuth.getInstance().currentUser?.email.orEmpty(),
                imageUrl   = hotel.imageUrl
            )

            reservationRepo.saveReservation(reservation)


            Log.d("RESERVA", "Reserva guardada correctament a Room: ${reservation}")

            _uiState.value = _uiState.value.copy(reservaConfirmada = true, error = null)

        }
    }

    fun resetReservaConfirmada() {
        _uiState.value = _uiState.value.copy(reservaConfirmada = false)
    }



}
