package com.example.waygo.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.waygo.domain.model.Trip

@Composable
fun TripRow(
    trip: Trip,
    hasRes: Boolean,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(trip.title, style = MaterialTheme.typography.titleLarge)
            if (hasRes) {
                Text(
                    text = "Inclou reserva d'hotel",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        if (hasRes) {
            Icon(
                imageVector        = Icons.Filled.Place,
                contentDescription = "Hotel Reserved",
                modifier           = Modifier.padding(start = 8.dp)
            )
        }
    }
}
