package com.example.waygo.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.waygo.models.Trip
import com.example.waygo.viewmodel.TripViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripListScreen(
    navController: NavController,
    viewModel: TripViewModel = viewModel()
) {
    val trips by viewModel.trips.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Els meus viatges") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("addTrip")
            }) {
                Text("+") // O pots posar un icona amb Icon(Icons.Default.Add, contentDescription = null)
            }
        }

    ) { paddingValues ->
        if (trips.isEmpty()) {
            Box(modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text("Encara no tens cap viatge.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                items(trips) { trip ->
                    TripItem(
                        trip = trip,
                        onDelete = { viewModel.deleteTrip(trip.id) },
                        onClick = {
                            // En un futur, pots navegar a la pantalla de detall
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TripItem(
    trip: Trip,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = trip.title, style = MaterialTheme.typography.titleMedium)
                Text(text = trip.description, style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onDelete) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Elimina")
            }
        }
    }
}
