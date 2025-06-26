package com.example.waygo.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.waygo.ui.viewmodel.AllReservationsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllReservationsScreen(
    navController: NavController,
    vm: AllReservationsViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) { vm.loadAll() }

    // 1) Importa getValue i renombra la llista
    val reservationsWithTrip by vm.items.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = { Text("Totes les reserves") })
    }) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize()
        ) {
            if (reservationsWithTrip.isEmpty()) {
                item {
                    Text(
                        "Cap reserva trobada",
                        Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                // 2) items(...) ara es refereix a la funció correcte
                items(reservationsWithTrip) { (res, trip) ->
                    Card(
                        Modifier
                            .fillMaxSize()
                            .padding(8.dp)

                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text("Viatge: ${trip?.title ?: "—"}",
                                style = MaterialTheme.typography.titleMedium)
                            Text("Hotel: ${res.hotelName}")
                            Text("Habitació: ${res.roomType}")
                            Text("Dates: ${res.startDate} → ${res.endDate}")
                            Text("Preu: €${res.price}")
                        }
                    }
                }
            }
        }
    }
}
