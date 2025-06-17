package com.example.waygo.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.waygo.data.remote.model.Hotel

@Composable
fun HotelList(hotels: List<Hotel>) {
    LazyColumn {
        if (hotels.isEmpty()) {
            item {
                Text("No hotels found.")
            }
        } else {
            items(hotels) { hotel ->
                HotelCard(hotel)
            }
        }
    }
}
