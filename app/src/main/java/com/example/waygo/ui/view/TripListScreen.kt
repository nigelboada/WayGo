package com.example.waygo.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.waygo.BuildConfig
import com.example.waygo.ui.viewmodel.TripViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Alignment
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.filled.Place
import androidx.compose.foundation.lazy.items


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripListScreen(
    navController: NavController,
    viewModel: TripViewModel = hiltViewModel()
) {
    // 1) Els viatges
    val trips by viewModel.trips.collectAsState()
    // 2) El mapa tripId → llista de reserves
    val tripResMap by viewModel.tripReservations.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadTrips()
        // si vols refrescar reserves aquí, pots cridar viewModel.loadAllReservations()
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
            Box(Modifier.padding(paddingValues).fillMaxSize()) {
                Text("No hi ha viatges afegits.", Modifier.padding(16.dp))
            }
        } else {
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier.fillMaxSize()
            ) {
                items(trips) { trip ->
                    val reservationsForThisTrip = tripResMap[trip.id].orEmpty()
                    val hasReservation = reservationsForThisTrip.isNotEmpty()

                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                if (hasReservation) {
                                    val firstRes = reservationsForThisTrip.first()
                                    navController.navigate(
                                        "reservationDetail/${BuildConfig.GROUP_ID}/${trip.id}/${firstRes.id}"
                                    )
                                } else {
                                    navController.navigate("itinerary_list/${trip.id}")
                                }
                            }
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(trip.title, style = MaterialTheme.typography.titleLarge)

                            if (hasReservation) {
                                Spacer(Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Place,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(Modifier.width(4.dp))
                                    Text(
                                        "Inclou reserva d'hotel",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }

                            Spacer(Modifier.height(8.dp))
                            Text(trip.description, style = MaterialTheme.typography.bodyMedium)
                            Spacer(Modifier.height(4.dp))
                            Text("📍 ${trip.location}")
                            Text("🗓️ ${trip.startDate} - ${trip.endDate}")

                            if (trip.activities.isNotEmpty()) {
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    "Activitats:",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )

                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
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
                            } else {
                                Spacer(Modifier.height(8.dp))
                                Text("Cap activitat afegida", style = MaterialTheme.typography.bodySmall)
                            }

                            Spacer(Modifier.height(12.dp))

                            Row {
                                Button(
                                    onClick = { navController.navigate("edit_trip/${trip.id}") },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Edita")
                                }
                                Spacer(Modifier.width(8.dp))
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