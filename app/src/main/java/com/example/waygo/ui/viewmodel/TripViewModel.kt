package com.example.waygo.ui.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waygo.domain.model.Itinerary
import com.example.waygo.domain.model.Reservation
import com.example.waygo.domain.model.Trip
import com.example.waygo.domain.repository.ActivityRepository
import com.example.waygo.domain.repository.ReservationRepository
import com.example.waygo.domain.repository.TripRepository
import com.example.waygo.utils.FileUtils
import com.google.firebase.auth.FirebaseAuth                  // ← aquest
import dagger.hilt.android.lifecycle.HiltViewModel           // ← i aquest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@HiltViewModel
class TripViewModel @Inject constructor(
    private val tripRepository: TripRepository,
    private val reservationRepository: ReservationRepository,
    private val tripImageRepository: TripRepository
) : ViewModel() {

    private val _activities = MutableStateFlow<List<Itinerary>>(emptyList())
    val activities: StateFlow<List<Itinerary>> = _activities


    private val _tripImages = MutableStateFlow<List<String>>(emptyList())
    val tripImages: StateFlow<List<String>> = _tripImages


    private var currentTripId: String = ""


    private val _trips = MutableStateFlow<List<Trip>>(emptyList())
    val trips: StateFlow<List<Trip>> = _trips

    private val _reservations = MutableStateFlow<List<Reservation>>(emptyList())
    val reservations: StateFlow<List<Reservation>> = _reservations

    // ➊ Estat per guardar totes les reserves agrupades per tripId
    private val _tripReservations = MutableStateFlow<Map<String, List<Reservation>>>(emptyMap())
    val tripReservations: StateFlow<Map<String, List<Reservation>>> = _tripReservations

    private val userId: String
        get() = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()

    init {
        loadTrips()
        loadAllReservations()        // ➋ les carreguem en iniciar
    }

    internal fun loadTrips() {
        viewModelScope.launch {
            val trips = tripRepository.getAllTripsForUser(userId)
            val tripsWithActivities = trips.map { trip ->
                val activities = ActivityRepository.getItemsForTrip(trip.id)
                trip.copy(activities = activities)
            }
            _trips.value = tripsWithActivities

            loadAllReservations()
        }
    }

    fun addTrip(title: String, description: String, startDate: String, endDate: String, location: String) {
        val newTrip = Trip(
            title = title,
            description = description,
            startDate = startDate,
            endDate = endDate,
            location = location
        )
        viewModelScope.launch {
            tripRepository.addTrip(newTrip, userId)
            loadTrips()
        }
    }

    fun updateTrip(tripId: String, title: String, description: String, location: String, startDate: String, endDate: String) {
        val updatedTrip = Trip(
            id = tripId,
            title = title,
            description = description,
            location = location,
            startDate = startDate,
            endDate = endDate
        )

        viewModelScope.launch {
            tripRepository.updateTrip(updatedTrip, userId)
            loadTrips()
        }
    }

    fun deleteTrip(trip: Trip) {
        viewModelScope.launch {
            tripRepository.deleteTrip(trip.id)
            loadTrips()
        }
    }

    private val _trip = MutableStateFlow<Trip?>(null)
    val trip: StateFlow<Trip?> = _trip

    fun getTripById(id: String) {
        viewModelScope.launch {
            val trip = tripRepository.getTripById(id)
            _trip.value = trip
        }
    }

    fun getDaysForTrip(tripId: String): List<String> {
        val trip = trips.value.find { it.id == tripId } ?: return emptyList()

        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val start = LocalDate.parse(trip.startDate, formatter)
        val end = LocalDate.parse(trip.endDate, formatter)

        val days = mutableListOf<String>()
        var current = start
        while (!current.isAfter(end)) {
            days.add(current.format(formatter))
            current = current.plusDays(1)
        }
        return days
    }

    fun getReservationsForTrip(tripId: String) {
        viewModelScope.launch {
            _reservations.value = reservationRepository.getReservationsForTrip(tripId)
        }
    }


    fun saveReservation(reservation: Reservation) {
        viewModelScope.launch {
            reservationRepository.saveReservation(reservation)
            // Recarrega les reserves del viatge actual després de guardar
            getReservationsForTrip(reservation.tripId)
        }
    }

    fun getActiveTrips(): List<Trip> {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        Log.d("TripDebug", "Avui és: $today")
        Log.d("TripDebug", "Viatges disponibles: ${trips.value}")

        trips.value.forEach { trip ->
            Log.d("TripDebug", "Viatge: ${trip.title} - start: ${trip.startDate}, end: ${trip.endDate}")
        }

        return trips.value.filter { trip ->
            try {
                val startDate = LocalDate.parse(trip.startDate, formatter)
                val endDate = LocalDate.parse(trip.endDate, formatter)
                val isActive = !today.isAfter(endDate)
                Log.d("TripDebug", "Viatge ${trip.title} és actiu? $isActive")
                isActive
            } catch (e: Exception) {
                Log.e("TripViewModel", "Error parsing trip dates: ${e.localizedMessage}")
                false
            }
        }
    }


    // 2) Mètode per recollir-les de la DB
    fun getImagesForTrip(tripId: String) {
        viewModelScope.launch {
            _tripImages.value = tripRepository.getImagesForTrip(tripId)
        }
    }


    // crida per desar-les
    fun addTripImages(tripId: String, uris: List<Uri>, context: Context) = viewModelScope.launch {
        // 1) copia cada URI a fitxer intern i recull rutes
        val savedPaths = uris.mapNotNull { uri ->
            FileUtils.copyUriToInternal(context, uri, tripId)
        }
        // 2) desa les rutes
        tripRepository.addImagesToTrip(tripId, savedPaths)
        // 3) recarrega
        loadTripImages(tripId)
    }


    fun addTripImageFromBitmap(tripId: String, bitmap: Bitmap, context: Context) = viewModelScope.launch {
        val path = FileUtils.saveBitmapToInternal(context, bitmap, tripId)
        if (path != null) {
            tripRepository.addImagesToTrip(tripId, listOf(path))
            loadTripImages(tripId)
        }
    }



    // crida per recuperar-les
    fun loadTripImages(tripId: String) = viewModelScope.launch {
        _tripImages.value = tripRepository.getImagesForTrip(tripId)
    }

    /** ➋ Carrega totes les reserves de l'usuari i les agrupa per tripId */
    private fun loadAllReservations() = viewModelScope.launch {
        val all = reservationRepository
            .getAllReservationsForUser(userId)    // retorna List<Reservation>
        _tripReservations.value = all.groupBy { it.tripId }
    }



    // ja tenim:
    fun deleteReservation(reservationId: String) = viewModelScope.launch {
        // 1. Elimina de llista local immediatament
        _reservations.value = _reservations.value.filterNot { it.id == reservationId }

        // 2. Crida al repositori per eliminar-la de la DB
        val ok = reservationRepository.deleteReservation(reservationId)

        // 3. Si falla, pots tornar-la a afegir (opcional)
        if (!ok) {
            loadTrips() // o pots recarregar només les reserves del viatge
            Log.e("TripViewModel", "Error esborrant reserva $reservationId")
        }
    }


}

