package com.example.waygo.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.waygo.ui.viewmodel.ReservationDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationDetailScreen(
    groupId: String,
    tripId: String,
    reservationId: String,
    navController: NavHostController,
    vm: ReservationDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(groupId, tripId, reservationId) {
        vm.load(groupId, tripId, reservationId)
    }


    val resState by vm.reservation.collectAsState()
    val hotelImg by vm.hotelImage.collectAsState()
    val roomImg  by vm.roomImage.collectAsState()

    Scaffold(
        topBar = { /* ... */ }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Copiem el valor a una val local
            val reservation = resState
            if (reservation == null) {
                Text("Reserva no trobada", color = Color.Red)
                return@Column
            }

            // A partir d’aquí el compilador sap que 'reservation' no és null
            Text("Hotel: ${reservation.hotelName}", style = MaterialTheme.typography.titleLarge)
            hotelImg?.let { url ->
                Image(
                    painter = rememberAsyncImagePainter(url),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.height(16.dp))

            Text("Habitació: ${reservation.roomType}", style = MaterialTheme.typography.titleMedium)
            roomImg?.let { url ->
                Image(
                    painter = rememberAsyncImagePainter(url),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.height(16.dp))

            Text("Dates: ${reservation.startDate} → ${reservation.endDate}")
            Text("Preu total: €${reservation.price}")
            Text("Correu: ${reservation.guestEmail}")
        }
    }
}
