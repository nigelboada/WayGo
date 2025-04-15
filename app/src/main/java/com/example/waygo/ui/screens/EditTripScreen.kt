package com.example.waygo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.waygo.viewmodel.TripViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTripScreen(
    navController: NavController,
    tripId: String,
    viewModel: TripViewModel = viewModel()
) {
    val trip = viewModel.getTripById(tripId)

    var title by remember { mutableStateOf(trip?.title ?: "") }
    var description by remember { mutableStateOf(trip?.description ?: "") }

    if (trip == null) {
        Text("Viatge no trobat")
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Editar Viatge") })
        }
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues).padding(16.dp)) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Títol") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripció") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.updateTrip(trip.copy(title = title, description = description))
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Desa els canvis")
            }
        }
    }
}
