package com.example.waygo.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waygo.domain.model.Trip
import com.example.waygo.domain.repository.TripRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TripViewModel @Inject constructor(
    private val repository: TripRepository
) : ViewModel() {

    private val TAG = "TripViewModel"

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _trips = mutableStateListOf<Trip>()
    val trips: List<Trip> get() = _trips

    private var currentUserId: String? = null

    init {
        val user = auth.currentUser
        currentUserId = user?.uid
        loadTrips()
    }

    private fun loadTrips() {
        Log.d(TAG, "Cargando viajes para el usuario actual...")
        _trips.clear()
        viewModelScope.launch {
            //_trips.addAll(repository.getAllTrips())
            currentUserId?.let { userId ->
                _trips.addAll(repository.getTripsByUserId(userId.hashCode()))
                Log.d(TAG, "Viajes cargados: ${_trips.size} viajes encontrados para el usuario $userId.")
            } ?: run {
                Log.d(TAG, "No hay usuario autenticado.")
            }
        }
        Log.d("TripViewModel", "Viajes cargados: ${_trips.size} viajes encontrados.")
    }

    fun addTrip(destination: String, startDate: Date, endDate: Date, budget: Double, notes: String, isFavorite: Boolean, coverImageUrl: String?) {
        if (destination.isNotEmpty()) {
            Log.d(TAG, "Intentando agregar un nuevo viaje a $destination.")
            val newTrip = Trip(
                id = 0,
                destination = destination,
                startDate = startDate,
                endDate = endDate,
                budget = budget,
                notes = notes,
                isFavorite = isFavorite,
                coverImageUrl = coverImageUrl ?: "",
                userId = currentUserId!!.hashCode(),
                itineraryItems = emptyList()
            )

            viewModelScope.launch {
                if (repository.addTrip(newTrip)) {
                    loadTrips()
                    Log.d("TripViewModel", "Viaje a $destination agregado exitosamente.")
                } else {
                    Log.d("TripViewModel", "No se pudo agregar el viaje a $destination.")
                }
            }
        }
    }

    fun deleteTrip(tripId: Int) {
        Log.d("TripViewModel", "Intentando eliminar el viaje con ID: $tripId")
        viewModelScope.launch {
            if (repository.deleteTrip(tripId)) {
                _trips.removeAll { it.id == tripId }
                Log.d("TripViewModel", "Viaje con ID: $tripId eliminado exitosamente.")
            } else {
                Log.d("TripViewModel", "No se pudo eliminar el viaje con ID: $tripId.")
            }
        }
    }

    fun updateTrip(updatedTrip: Trip) {
        Log.d("TripViewModel", "Intentando actualizar el viaje con ID: ${updatedTrip.id}")
        viewModelScope.launch {
            if (repository.updateTrip(updatedTrip)) {
                val index = _trips.indexOfFirst { it.id == updatedTrip.id }
                if (index != -1) {
                    _trips[index] = updatedTrip
                    Log.d("TripViewModel", "Viaje con ID: ${updatedTrip.id} actualizado correctamente.")
                }
            } else {
                Log.d("TripViewModel", "No se pudo actualizar el viaje con ID: ${updatedTrip.id}.")
            }
        }
    }

}
