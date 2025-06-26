// app/src/main/java/com/example/waygo/ui/view/AllReservationsScreen.kt
package com.example.waygo.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.waygo.ui.viewmodel.AllReservationsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllReservationsScreen(
    vm: AllReservationsViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        vm.loadAll()
    }

    val items by vm.items.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = { Text("Totes les reserves") })
    }) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize()
        ) {
            if (items.isEmpty()) {
                item {
                    Text(
                        "Cap reserva trobada",
                        Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                items(items) { (res, trip) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // imatge de l’hotel
                            Image(
                                painter = rememberAsyncImagePainter(res.hotelImageUrl),
                                contentDescription = "Hotel",
                                modifier = Modifier.size(48.dp),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(Modifier.width(8.dp))
                            // imatge de l’habitació
                            Image(
                                painter = rememberAsyncImagePainter(res.roomImageUrl),
                                contentDescription = "Habitació",
                                modifier = Modifier.size(48.dp),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = trip?.title ?: "—",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "${res.hotelName} • ${res.roomType}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "${res.startDate} → ${res.endDate}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                            IconButton(onClick = { vm.deleteReservation(res.id) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Esborra")
                            }
                        }
                    }
                }
            }
        }
    }
}
