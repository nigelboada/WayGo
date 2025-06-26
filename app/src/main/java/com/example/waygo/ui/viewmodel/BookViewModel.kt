package com.example.waygo.ui.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.example.waygo.data.remote.model.Hotel
import com.example.waygo.domain.repository.HotelRepository
import java.time.LocalDate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waygo.BuildConfig
import com.example.waygo.data.remote.dto.RoomDto
import com.example.waygo.domain.model.Reservation
import com.example.waygo.domain.repository.ReservationRepository
import com.example.waygo.utils.ErrorUtils

import com.google.firebase.auth.FirebaseAuth


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import retrofit2.HttpException
import java.util.UUID



@HiltViewModel
class BookViewModel @Inject constructor(
    private val hotelRepo: HotelRepository,
    private val reservationRepo: ReservationRepository
) : ViewModel() {

    val groupId = BuildConfig.GROUP_ID

    private val _uiState = MutableStateFlow(BookUiState())
    val uiState: StateFlow<BookUiState> = _uiState

    /* ---------- city picker ---------- */
    fun toggleCityMenu() = _uiState.update { it.copy(cityMenu = !it.cityMenu) }
    fun selectCity(c: String) = _uiState.update { it.copy(city = c, cityMenu = false) }

    /* ---------- date pickers ---------- */
    fun pickStart(d: LocalDate) = _uiState.update { it.copy(startDate = d) }
    fun pickEnd(d: LocalDate)   = _uiState.update { it.copy(endDate = d) }

    /* ---------- search ---------- */
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun search() = viewModelScope.launch {
        val s = _uiState.value.startDate ?: return@launch
        val e = _uiState.value.endDate   ?: return@launch
        val fmt = DateTimeFormatter.ISO_DATE
        val city = _uiState.value.city

        _uiState.update { it.copy(loading = true, message = null) }

        try {
            val hotels = hotelRepo.getAvailability(groupId, s.format(fmt), e.format(fmt), city = city)
            _uiState.update { it.copy(loading = false, hotels = hotels) }
        } catch (e: HttpException) {

            val decodedError = ErrorUtils.extractErrorMessage(e)

            Log.e("BookViewModel", "HTTP error: $decodedError  $e")
            _uiState.update { it.copy(loading = false, hotels = emptyList(), message = decodedError) }

            _uiState.update {
                it.copy(
                    loading = false,
                    hotels = emptyList(),
                    message = "Error: ${decodedError}}"
                )
            }

        } catch (e: Exception) {
            Log.e("BookViewModel", "Error: ${e.localizedMessage}")
//            _uiState.update { it.copy(loading = false, hotels = emptyList()) }
            _uiState.update {
                it.copy(
                    loading = false,
                    hotels = emptyList(),
                    message = "Error: ${e.message}}"
                )
            }
        }
    }

    fun bookRoom(
                tripId: String,
                hotel: Hotel,
                room: RoomDto
            ) = viewModelScope.launch {
                // format de dates
                val fmt = DateTimeFormatter.ISO_DATE
                val start = uiState.value.startDate!!.format(fmt)
                val end   = uiState.value.endDate!!.format(fmt)

                // email de l’usuari
                val email = FirebaseAuth.getInstance().currentUser?.email.orEmpty()

                val r = Reservation(
                        id         = UUID.randomUUID().toString(),
                        tripId     = tripId,
                        hotelId    = hotel.id,                // Hotel.id a data.remote.model
                        hotelName  = hotel.name,
                        roomId     = room.id,
                        roomType   = room.roomType ?: "Unknown",
                    price      = room.price ?: 0f,              // Float
                    startDate  = start,                   // String
                    endDate    = end,                     // String
                    guestEmail = email,
                    hotelImageUrl = hotel.imageUrl,      // String
                    roomImageUrl = room.images?.firstOrNull() ?: "" // String
                )

                reservationRepo.saveReservation(r)
                _uiState.update { it.copy(message = "Reserva desada!") }
            }


}


data class BookUiState(
    val loading: Boolean = false,
    val cityMenu: Boolean = false,
    val city: String = "Barcelona",
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val hotels: List<Hotel> = emptyList(),
    val message: String? = null
)