package com.example.waygo.ui.search

import com.example.waygo.data.remote.model.Hotel

data class SearchUiState(
    val selectedCity: String = "Barcelona",
    val startDate: String = "",
    val endDate: String = "",
    val isLoading: Boolean = false,
    val hotels: List<Hotel> = emptyList(),
    val error: String? = null
)
