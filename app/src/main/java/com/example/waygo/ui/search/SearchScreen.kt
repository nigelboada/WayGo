package com.example.waygo.ui.search

import android.util.Log
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController


import com.example.waygo.ui.components.*


@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Mostrar snackbar quan la reserva es confirma
    LaunchedEffect(state.reservaConfirmada) {
        if (state.reservaConfirmada) {
            snackbarHostState.showSnackbar("Reserva guardada correctament!")
            // Reiniciar el flag per evitar que es repeteixi
            viewModel.resetReservaConfirmada()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(modifier = Modifier.padding(16.dp).padding(padding)) {

            Text("Search Hotels", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))

            DropdownMenuCitySelector(
                selectedCity = state.selectedCity,
                onCitySelected = { viewModel.updateCity(it) }
            )

            DatePickerField("Start Date", state.startDate) { viewModel.updateDates(it, state.endDate) }
            DatePickerField("End Date", state.endDate) { viewModel.updateDates(state.startDate, it) }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.searchHotels() },
                enabled = state.startDate.isNotBlank() && state.endDate.isNotBlank()
            ) {
                Text("Search")
            }

            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
                state.error != null -> {
                    Text("Error: ${state.error}", color = Color.Red)
                }
                state.hotels.isEmpty() -> {
                    Text("No s'han trobat hotels.", color = Color.Gray)
                }
                else -> {
                    HotelList(hotels = state.hotels) { hotel ->
                        navController.navigate("hotel_detail/${hotel.id}/${hotel.groupId}/${state.startDate}/${state.endDate}")
                    }
                }
            }
        }
    }
}
