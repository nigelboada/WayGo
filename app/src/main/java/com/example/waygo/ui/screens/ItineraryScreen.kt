package com.example.waygo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.waygo.models.ItineraryItem
import com.example.waygo.viewmodel.ItineraryViewModel
import com.example.waygo.viewmodel.ItineraryViewModelFactory
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItineraryScreen(
    navController: NavController,
    tripId: String
) {
    val viewModel: ItineraryViewModel = viewModel(
        factory = ItineraryViewModelFactory(tripId)
    )

    val activities = viewModel.itinerary.collectAsState().value

    var showDialog by remember { mutableStateOf(false) }
    var newActivityName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Itinerari") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Afegir activitat")
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            if (activities.isEmpty()) {
                Text("No hi ha activitats afegides.")
            } else {
                LazyColumn {
                    items(activities) { activity ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(text = activity.title, style = MaterialTheme.typography.titleMedium)
                                Spacer(modifier = Modifier.height(4.dp))
                                Row {
                                    TextButton(onClick = {
                                        viewModel.deleteActivity(activity.id)
                                    }) {
                                        Text("🗑️ Elimina")
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    TextButton(onClick = {
                                        newActivityName = activity.title
                                        showDialog = true
                                        // Pots afegir edició real amb ID si ho vols fer més pro
                                    }) {
                                        Text("✏️ Edita")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    if (newActivityName.isNotBlank()) {
                        val newItem = ItineraryItem(
                            title = newActivityName,
                            description = "",
                            time = ""
                        )

                        viewModel.addActivity(newItem)
                        newActivityName = ""
                        showDialog = false
                    }
                }) {
                    Text("Afegir")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                }) {
                    Text("Cancel·la")
                }
            },
            title = { Text("Nova activitat") },
            text = {
                OutlinedTextField(
                    value = newActivityName,
                    onValueChange = { newActivityName = it },
                    label = { Text("Nom de l’activitat") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
    }
}
