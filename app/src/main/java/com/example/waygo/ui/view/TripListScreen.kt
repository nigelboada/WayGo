package com.example.waygo.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.waygo.BuildConfig
import com.example.waygo.domain.model.Trip
import com.example.waygo.ui.viewmodel.TripViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripListScreen(
    navController: NavController,
    viewModel: TripViewModel = hiltViewModel()
) {
    // 1) Observem els viatges i també el mapa tripId → reserves
    val trips by viewModel.trips.collectAsState()
    val tripResMap by viewModel.tripReservations.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadTrips()
        // Si vols recarregar reserves, pots cridar aquí viewModel.loadAllReservations()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Els meus viatges") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Tornar enrere")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("add_trip") }) {
                        Icon(Icons.Default.Add, contentDescription = "Afegir viatge")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (trips.isEmpty()) {
            Box(
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No hi ha viatges afegits.", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier.fillMaxSize()
            ) {
                items(trips) { trip ->
                    // Determine if this trip has reservations
                    val reservationsForThisTrip = tripResMap[trip.id].orEmpty()
                    val hasReservation = reservationsForThisTrip.isNotEmpty()

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                // Quan cliquem fora dels botons, anem sempre a la pantalla d’activitats i fotos
                                navController.navigate("itinerary_list/${trip.id}")
                            }
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            // Títol i descriptiu
                            Text(trip.title, style = MaterialTheme.typography.titleLarge)
                            Spacer(Modifier.height(4.dp))
                            Text(trip.description, style = MaterialTheme.typography.bodyMedium)
                            Spacer(Modifier.height(4.dp))
                            Text("📍 ${trip.location}", style = MaterialTheme.typography.bodySmall)
                            Text("🗓️ ${trip.startDate} - ${trip.endDate}", style = MaterialTheme.typography.bodySmall)

                            // Indicador de reserva si n'hi ha
                            if (hasReservation) {
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    "Inclou reserva d'hotel",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            // Activitats resumides
                            if (trip.activities.isNotEmpty()) {
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    "Activitats:",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                    trip.activities
                                        .sortedWith(compareBy({ it.day }, { it.hour }))
                                        .take(3)
                                        .forEach { activity ->
                                            Text(
                                                "• ${activity.day} ${activity.hour} - ${activity.title}",
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }
                                    if (trip.activities.size > 3) {
                                        Text(
                                            "+ ${trip.activities.size - 3} més...",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                }
                            }

                            Spacer(Modifier.height(12.dp))

                            // ----------------------------------------------------------------
                            // Fila amb tres botons: Edita | Reserves | Elimina
                            // ----------------------------------------------------------------
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // 1) Edita
                                Button(
                                    onClick = {
                                        navController.navigate("edit_trip/${trip.id}")
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Edita")
                                }


                                // 3) Elimina
                                Button(
                                    onClick = { viewModel.deleteTrip(trip) },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Elimina")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
