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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.waygo.domain.model.Trip
import com.example.waygo.ui.viewmodel.TripViewModel


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun TripListScreen(
    navController: NavController,
    viewModel: TripViewModel
) {
    val trips = viewModel.trips.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.loadTrips()
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
                    IconButton(onClick = {
                        navController.navigate("add_trip")
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Afegir viatge")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (trips.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
            ) {
                Text(
                    text = "No hi ha viatges afegits.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier.fillMaxSize()
            )

            {
                items(trips) { trip: Trip ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                navController.navigate("itinerary_list/${trip.id}")
                            }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(trip.title, style = MaterialTheme.typography.titleLarge)
                            Text(trip.description, style = MaterialTheme.typography.bodyMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("📍 ${trip.location}")
                            Text("🗓️ ${trip.startDate} - ${trip.endDate}")

                            // 🔽 Mostrem les activitats si n'hi ha
                            if (trip.activities.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "Activitats:",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )

                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    trip.activities
                                        .sortedWith(compareBy({ it.day }, { it.hour }))
                                        .take(3)
                                        .forEach { activity ->
                                            Text(
                                                text = "• ${activity.day} ${activity.hour} - ${activity.title}",
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }

                                    if (trip.activities.size > 3) {
                                        Text(
                                            text = "+ ${trip.activities.size - 3} més...",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                }
                            } else {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Cap activitat afegida", style = MaterialTheme.typography.bodySmall)
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Row {
                                Button(
                                    onClick = {
                                        navController.navigate("edit_trip/${trip.id}")
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Edita")
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Button(
                                    onClick = {
                                        viewModel.deleteTrip(trip)
                                    },
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