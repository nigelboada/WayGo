package com.example.waygo.ui.search

import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


import com.example.waygo.ui.components.*


@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
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

        Button(
            onClick = { viewModel.searchHotels() },
            enabled = state.startDate.isNotBlank() && state.endDate.isNotBlank()
        ) {
            Text("Search")
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        } else if (state.error != null) {
            Text("Error: ${state.error}", color = Color.Red)
        } else {
            HotelList(hotels = state.hotels)
        }
    }
}
