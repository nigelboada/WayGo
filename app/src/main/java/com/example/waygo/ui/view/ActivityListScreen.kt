package com.example.waygo.ui.view

import android.graphics.Bitmap
import androidx.activity.result.contract.ActivityResultContracts.TakePicturePreview



import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
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

    var expandedImageUri by remember { mutableStateOf<String?>(null) }
    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }


    val context = LocalContext.current


    // Galeria
    val pickImagesLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenMultipleDocuments()
    ) { uris: List<Uri> ->
        tripViewModel.addTripImages(tripId, uris, context)
    }


    // Càmera
    val takePhotoLauncher = rememberLauncherForActivityResult(
        TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            tripViewModel.addTripImageFromBitmap(
                tripId,
                it,
                context
            )
        }
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
                title = { Text("Informació del viatge") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Tornar")
                    }
                },
                actions = {
                    var menuExpanded by remember { mutableStateOf(false) }

                    Box {
                        TextButton(onClick = { menuExpanded = true }) {
                            Text("Afegir foto")
                        }
                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Triar de la galeria") },
                                onClick = {
                                    menuExpanded = false
                                    pickImagesLauncher.launch(arrayOf("image/*"))
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Fer una foto") },
                                onClick = {
                                    menuExpanded = false
                                    takePhotoLauncher.launch(null)
                                }
                            )
                        }
                    }

                    Spacer(Modifier.width(8.dp))

//                    TextButton(onClick = { navController.navigate("add_activity/$tripId") }) {
//                        Text("Afegir activitat")
//                    }
                }
            )


        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {
            // ────────── 1) LES MINIATURES ──────────
            if (tripImages.isNotEmpty()) {
                item {
                    Text(
                        text = "Fotos del viatge",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                    )
                }
                items(tripImages) { uriString ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(uriString),
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .clickable {
                                    // quan cliquem guardem l’URI a expandedImageUri
                                    expandedImageUri = uriString
                                },
                            contentScale = ContentScale.Crop
                        )
                        Spacer(Modifier.width(8.dp))
                        IconButton(onClick = {
                            tripViewModel.deleteTripImage(tripId, uriString)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Esborra foto",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
            // ACTIVITATS
//            if (filteredActivities.isEmpty()) {
//                item {
//                    Text(
//                        "No hi ha activitats afegides.",
//                        style = MaterialTheme.typography.bodyLarge,
//                        modifier = Modifier.padding(16.dp)
//                    )
//                }
//            } else {
//                items(filteredActivities, key = { it.id }) { activity ->
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(8.dp)
//                    ) {
//                        Column(modifier = Modifier.padding(16.dp)) {
//                            Text(activity.title, style = MaterialTheme.typography.titleLarge)
//                            Text(activity.description)
//                            Text("📅 Dia: ${activity.day}")
//                            Text("🕒 Hora: ${activity.hour}")
//
//                            Spacer(modifier = Modifier.height(8.dp))
//
//                            Button(
//                                onClick = {
//                                    activityViewModel.deleteActivity(activity.id)
//                                },
//                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
//                            ) {
//                                Text("Elimina")
//                            }
//
//                            Button(
//                                onClick = {
//                                    navController.navigate("edit_activity/${activity.id}/$tripId")
//                                }
//                            ) {
//                                Text("Editar")
//                            }
//                        }
//                    }
//                }
//            }


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

        if (expandedImageUri != null) {
            Dialog(onDismissRequest = {
                expandedImageUri = null
                scale = 1f; offsetX = 0f; offsetY = 0f
            }) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.8f))
                        .pointerInput(Unit) {
                            detectTransformGestures { _, pan, zoom, _ ->
                                // actualitzem escala i offset
                                scale = (scale * zoom).coerceIn(1f, 5f)
                                offsetX += pan.x
                                offsetY += pan.y
                            }
                        }
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(expandedImageUri),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                                translationX = offsetX
                                translationY = offsetY
                            }
                    )
                }
            }
        }
    }
}
