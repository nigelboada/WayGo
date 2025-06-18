package com.example.waygo.ui.view


import androidx.compose.foundation.Image
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
import androidx.navigation.NavController
import com.example.waygo.ui.viewmodel.ActivityViewModel
import com.example.waygo.ui.viewmodel.TripViewModel
import com.example.waygo.domain.model.Itinerary
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberAsyncImagePainter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityListScreen(
    tripId: String,
    navController: NavController,
    tripViewModel: TripViewModel,
    activityViewModel: ActivityViewModel
) {
    LaunchedEffect(tripId) {
        activityViewModel // potser hi poses un `activityViewModel.loadActivities(tripId)` si tens aquesta funció
    }

    val activities = activityViewModel.activities.collectAsState().value
    val filteredActivities = activities.filter { it.tripId == tripId }

    val reservations = remember(tripId) {
        tripViewModel.getReservationsForTrip(tripId)
    }.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Activitats del viatge") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Tornar")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("add_activity/$tripId")
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Afegir activitat")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {
            // ACTIVITATS
            if (filteredActivities.isEmpty()) {
                item {
                    Text(
                        "No hi ha activitats afegides.",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                items(filteredActivities, key = { it.id }) { activity ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(activity.title, style = MaterialTheme.typography.titleLarge)
                            Text(activity.description)
                            Text("📅 Dia: ${activity.day}")
                            Text("🕒 Hora: ${activity.hour}")

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = {
                                    activityViewModel.deleteActivity(activity.id)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                            ) {
                                Text("Elimina")
                            }

                            Button(
                                onClick = {
                                    navController.navigate("edit_activity/${activity.id}/$tripId")
                                }
                            ) {
                                Text("Editar")
                            }
                        }
                    }
                }
            }

            // RESERVES
            if (reservations.isNotEmpty()) {
                item {
                    Text(
                        "Reserves d’habitació",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 16.dp, top = 24.dp)
                    )
                }

                items(reservations) { res ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Row(modifier = Modifier.padding(8.dp)) {
                            // 🖼️ Imatge de l’hotel
                            Image(
                                painter = rememberAsyncImagePainter(res.imageUrl),
                                contentDescription = "Imatge de l’hotel",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(80.dp)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            // 📄 Detalls de la reserva
                            Column {
                                Text("🏨 Hotel: ${res.hotelName}", fontWeight = FontWeight.Bold)
                                Text("🛏 Habitació: ${res.roomType} (${res.roomId})")
                                Text("💰 Preu: ${res.price}€")
                                Text("📆 Del ${res.startDate} al ${res.endDate}")
                            }
                        }
                    }
                }
            }
        }
    }
}
