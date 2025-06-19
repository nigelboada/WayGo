package com.example.waygo.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waygo.data.local.entity.ReservationEntity
import com.example.waygo.data.remote.model.Hotel
import com.example.waygo.data.remote.model.ReserveRequest
import com.example.waygo.data.remote.model.Room
import com.example.waygo.domain.repository.HotelRepository
import com.example.waygo.domain.repository.ReservationRepository
import com.example.waygo.utils.ErrorUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HotelDetailViewModel @Inject constructor(
    private val repo: HotelRepository,
    private val reservationRepository: ReservationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HotelDetailUiState())
    val uiState: StateFlow<HotelDetailUiState> = _uiState

    var showImageDialog by mutableStateOf(false)
    fun showImageDialog() { showImageDialog = true }
    fun hideImageDialog() { showImageDialog = false }

    fun selectRoom(room: Room) {
        _uiState.value = _uiState.value.copy(selectedRoom = room)
    }

    private lateinit var groupId: String
    private lateinit var start: String
    private lateinit var end: String

    /* -------- load hotel & free rooms -------- */
    fun load(hotelId: String, gid: String, s: String, e: String) {
        if (uiState.value.hotel != null) return   // already loaded
        groupId = gid; start = s; end = e
        viewModelScope.launch {
            val hotel = repo.getHotels(gid).first { it.id == hotelId }
            val freeRooms = repo.getAvailability(gid, s, e)
                .first { it.id == hotelId }.rooms
            _uiState.value = HotelDetailUiState(false, hotel, freeRooms)
        }
    }

//    fun reserveRoom(selectedRoom: Room) {
//        Log.d("hoteldetail", "room: $selectedRoom")
//        Log.i("hoteldetail", "room: $selectedRoom")
//        Log.d("hoteldetail", "room: $selectedRoom")
//    }

    /* -------- reserve selected room -------- */
    fun reserveRoom(room: Room, tripId: String) = viewModelScope.launch {
        Log.d("reserveRoom called", "room: $room")

        val req = ReserveRequest(
            hotelId = uiState.value.hotel!!.id,
            roomId = room.id,
            startDate = start,
            endDate = end,
            guestName = "Nigel", // substituir per usuari real
            guestEmail = "nboadag@gmail.com"
        )

        try {
            val result = repo.reserveRoom(groupId, req) // si retorna true

            if (result) {
                val reservationEntity = ReservationEntity(
                    id = room.id + tripId,
                    tripId = tripId,
                    hotelId = uiState.value.hotel!!.id,
                    hotelName = uiState.value.hotel!!.name,
                    roomId = room.id,
                    roomType = room.roomType,
                    price = room.price,
                    startDate = start,
                    endDate = end,
                    guestEmail = req.guestEmail,
                    imageUrl = room.images.firstOrNull() ?: ""
                )

                reservationRepository.saveReservation(reservationEntity)
                Log.d("reserveRoom", "Reserva guardada correctament a Room")
            }

        } catch (e: HttpException) {
            val decodedError = ErrorUtils.extractErrorMessage(e)
            Log.e("HotelDetailViewModel", "HTTP error: $decodedError  $e")

        } catch (e: Exception) {
            Log.e("HotelDetailViewModel", "Error: ${e.localizedMessage}")
        }
    }


}



data class HotelDetailUiState(
    val loading: Boolean = true,
    val hotel: Hotel? = null,
    val rooms: List<Room>? = emptyList(),
    val selectedRoom: Room? = null,
    val showImageDialog: Boolean = false,
)