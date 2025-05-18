package com.example.waygo.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waygo.domain.model.Hotel
import com.example.waygo.domain.model.ReserveRequest
import com.example.waygo.domain.model.Room
import com.example.waygo.domain.repository.HotelRepository
import com.example.waygo.utils.ErrorUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HotelDetailViewModel @Inject constructor(
    private val repo: HotelRepository
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
    fun reserveRoom(room: Room) = viewModelScope.launch {

        Log.d("reserveRoom called", "room: $room")

        val req = ReserveRequest(
            hotelId = uiState.value.hotel!!.id,
            roomId  = room.id,
            startDate = start,
            endDate   = end,
            guestName = "Vitor", //CAMBIAR AQUI PARA TU USUARIO
            guestEmail = "vitorlui@gmail.com" //CAMBIAR AQUI PARA TU USUARIO
        )

        try {
            repo.reserve(groupId, req)   // we ignore response here
        } catch (e: HttpException) {
            val decodedError = ErrorUtils.extractErrorMessage(e)
            Log.e("BookViewModel", "HTTP error: ${decodedError}  $e")

        } catch (e: Exception) {
            Log.e("BookViewModel", "Error: ${e.localizedMessage}")
        }

    }
}


//try {
//    val hotels = repo.getAvailability(groupId, s.format(fmt), e.format(fmt), city = city)
//    _uiState.update { it.copy(loading = false, hotels = hotels) }
//} catch (e: HttpException) {
//
//    val decodedError = ErrorUtils.extractErrorMessage(e)
//
//    Log.e("BookViewModel", "HTTP error: ${decodedError}  $e")
//    _uiState.update { it.copy(loading = false, hotels = emptyList(), message = decodedError) }
//
//    _uiState.update {
//        it.copy(
//            loading = false,
//            hotels = emptyList(),
//            message = "Error: ${decodedError}}"
//        )
//    }
//
//} catch (e: Exception) {
//    Log.e("BookViewModel", "Error: ${e.localizedMessage}")
////            _uiState.update { it.copy(loading = false, hotels = emptyList()) }
//    _uiState.update {
//        it.copy(
//            loading = false,
//            hotels = emptyList(),
//            message = "Error: ${e.message}}"
//        )
//    }
//}

data class HotelDetailUiState(
    val loading: Boolean = true,
    val hotel: Hotel? = null,
    val rooms: List<Room>? = emptyList(),
    val selectedRoom: Room? = null,
    val showImageDialog: Boolean = false,
)