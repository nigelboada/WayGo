package com.example.waygo.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.waygo.domain.model.Reservation


@Composable
fun ReservationRow(
    res: Reservation,
    onCancel: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Substitueix guestName per guestEmail (o el camp que vulguis mostrar)
        // i hotel.name per hotelName
        Text(
            text = "${res.guestEmail} – ${res.hotelName}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onCancel) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Cancel"
            )
        }
    }
}
