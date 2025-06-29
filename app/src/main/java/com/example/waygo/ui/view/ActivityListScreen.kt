package com.example.waygo.ui.view


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.waygo.ui.viewmodel.ActivityViewModel
import com.example.waygo.ui.viewmodel.TripViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberAsyncImagePainter
import com.example.waygo.BuildConfig


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityListScreen(
    tripId: String,
    navController: NavController,
    tripViewModel: TripViewModel,
    activityViewModel: ActivityViewModel
) {


    val context = LocalContext.current


    val pickImagesLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenMultipleDocuments()
    ) { uris: List<Uri> ->
        // ara passem URIs i context directament
        tripViewModel.addTripImages(tripId, uris, context)
    }


    // Carrega dades inicials
    LaunchedEffect(tripId) {
        tripViewModel.getTripById(tripId)
        tripViewModel.loadTripImages(tripId)
        tripViewModel.getReservationsForTrip(tripId)

        // Afegir aquí activityViewModel.loadActivities(tripId) si en tens
    }

    val activities = activityViewModel.activities.collectAsState().value
    val filteredActivities = activities.filter { it.tripId == tripId }

    val reservations = tripViewModel.reservations.collectAsState().value
    val tripImages = tripViewModel.tripImages.collectAsState().value








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
                    // Fem un Column dins d’accions, perquè es mostrin en vertical
                    Column(
                        modifier = Modifier
                            .padding(end = 8.dp)               // una mica de marge amb el costat dret
                            .wrapContentWidth(Alignment.End),  // alineat a la dreta
                        verticalArrangement = Arrangement.spacedBy(4.dp)  // separació entre botons
                    ) {
                        TextButton(onClick = { pickImagesLauncher.launch(arrayOf("image/*")) }) {
                            Text("Afegir foto")
                        }
//                        TextButton(onClick = { navController.navigate("add_activity/$tripId") }) {
//                            Text("Afegir activitat")
//                        }
                    }
                }
            )


        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {

            if (tripImages.isNotEmpty()) {
                item {
                    Text(
                        text = "Fotos del viatge",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                    )
                }
                items(tripImages) { uriString ->
                    Image(
                        painter = rememberAsyncImagePainter(uriString),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(100.dp)
                            .clip(MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop
                    )
                }
            }
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

                items(reservations, key = { it.id }) { res ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable {
                                    navController.navigate("reservationDetail/${BuildConfig.GROUP_ID}/$tripId/${res.id}")
                                }
                                .padding(8.dp)
                        ) {
                            // 1) Imatge
                            Image(
                                painter = rememberAsyncImagePainter(res.hotelImageUrl),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(80.dp)
                            )
                            Spacer(Modifier.width(8.dp))

                            // 2) Dades de la reserva
                            Column(modifier = Modifier.weight(1f)) {
                                Text("🏨 Hotel: ${res.hotelName}", fontWeight = FontWeight.Bold)
                                Text("🛏 Habitació: ${res.roomType} (${res.roomId})")
                                Text("💰 Preu: ${res.price}€")
                                Text("📆 Del ${res.startDate} al ${res.endDate}")
                            }

                            // 3) Botó d’esborrar
                            IconButton(
                                onClick = {
                                    tripViewModel.deleteReservation(res.id)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Esborra reserva",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            } else {
                item {
                    Text(
                        "No hi ha reserves d’habitació.",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}