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

    Column(modifier = Modifier.padding(16.dp)) {

        Log.d("UI_STATE", "Ciutat: ${state.selectedCity}, Start: ${state.startDate}, End: ${state.endDate}, Hotels: ${state.hotels.size}")

        Text("Search Hotels", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Ciutats
        DropdownMenuCitySelector(
            selectedCity = state.selectedCity,
            onCitySelected = { viewModel.updateCity(it) }
        )

        // Dates
        DatePickerField("Start Date", state.startDate) { viewModel.updateDates(it, state.endDate) }
        DatePickerField("End Date", state.endDate) { viewModel.updateDates(state.startDate, it) }

        Spacer(modifier = Modifier.height(16.dp))

        Log.d("DEBUG", "Ciutat: ${state.selectedCity}, Start: ${state.startDate}, End: ${state.endDate}")

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
                HotelList(hotels = state.hotels, onClick = { hotel ->
                    // podries navegar o fer print
                    Log.d("UI", "Clicat: ${hotel.name}")
                })
            }
        }


    }
}
