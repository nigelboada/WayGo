package com.example.waygo.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.waygo.viewmodel.TripViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripListScreen(
    navController: NavController,
    viewModel: TripViewModel = viewModel()
) {
    val trips = viewModel.trips.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Els meus viatges") },
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
            ) {
                items(trips) { trip ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                // Navegar a pantalla de detall si es vol
                                // navController.navigate("trip_detail/${trip.id}")
                            }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(trip.title, style = MaterialTheme.typography.titleLarge)
                            Text(trip.description, style = MaterialTheme.typography.bodyMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("📍 ${trip.location}")
                            Text("🗓️ ${trip.startDate} - ${trip.endDate}")
                        }
                    }
                }
            }
        }
    }
}
