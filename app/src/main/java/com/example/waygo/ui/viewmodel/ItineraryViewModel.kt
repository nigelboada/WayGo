package com.example.waygo.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waygo.domain.model.ItineraryItem
import com.example.waygo.domain.repository.ItineraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItineraryViewModel @Inject constructor(
    private val repository: ItineraryRepository
) : ViewModel() {

    private val _itineraryItems = MutableStateFlow<List<ItineraryItem>>(emptyList())
    val itineraryItems: StateFlow<List<ItineraryItem>> = _itineraryItems.asStateFlow()
    private val TAG = "ItineraryViewModel" // Definir un TAG para los logs

    fun getItineraryItems(tripId: Int): StateFlow<List<ItineraryItem>> {
        Log.d(TAG, "Solicitando itinerarios para el viaje con ID: $tripId")
        viewModelScope.launch {
            _itineraryItems.value = repository.getItineraryItems(tripId)
            Log.d(TAG, "Itinerarios obtenidos para el viaje con ID: $tripId")
        }
        return _itineraryItems
    }

    fun addItineraryItem(item: ItineraryItem) {
        Log.d(TAG, "Intentando agregar un nuevo itinerario con ID: ${item.id} para el viaje ${item.tripId}")
        viewModelScope.launch {
            if (repository.addItineraryItem(item)) {
                _itineraryItems.value = repository.getItineraryItems(item.tripId)
                Log.d(TAG, "Itinerario con ID: ${item.id} agregado correctamente")
            } else {
                Log.d(TAG, "No se pudo agregar el itinerario con ID: ${item.id}")
            }
        }
    }

    fun updateItineraryItem(item: ItineraryItem) {
        Log.d(TAG, "Intentando actualizar itinerario con ID: ${item.id} para el viaje ${item.tripId}")
        viewModelScope.launch {
            if (repository.updateItineraryItem(item)) {
                _itineraryItems.value = repository.getItineraryItems(item.tripId)
                Log.d(TAG, "Itinerario con ID: ${item.id} actualizado correctamente")
            } else {
                Log.d(TAG, "No se pudo actualizar el itinerario con ID: ${item.id}")
            }
        }
    }

    fun deleteItineraryItem(id: Int) {
        Log.d(TAG, "Intentando eliminar itinerario con ID: $id")
        viewModelScope.launch {
            if (repository.deleteItineraryItem(id)) {
                _itineraryItems.value = _itineraryItems.value.filter { it.id != id }
                Log.d(TAG, "Itinerario con ID: $id eliminado correctamente")
            } else {
                Log.d(TAG, "No se pudo eliminar el itinerario con ID: $id")
            }
        }
    }
}
