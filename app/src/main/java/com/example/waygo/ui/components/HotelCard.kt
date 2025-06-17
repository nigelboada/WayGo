package com.example.waygo.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.waygo.data.remote.model.Hotel

@Composable
fun HotelCard(hotel: Hotel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(hotel.name, style = MaterialTheme.typography.titleMedium)
            Text("Rooms: ${hotel.rooms.size}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
