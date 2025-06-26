// app/src/main/java/com/example/waygo/ui/components/ReservationRow.kt
package com.example.waygo.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.waygo.domain.model.Reservation

@Composable
fun ReservationRow(
    res: Reservation,
    onCancel: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 1) Imatge de l’hotel
            Image(
                painter = rememberAsyncImagePainter(res.hotelImageUrl),
                contentDescription = "Hotel",
                modifier = Modifier.size(48.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(8.dp))
            // 2) Imatge de l’habitació
            Image(
                painter = rememberAsyncImagePainter(res.roomImageUrl),
                contentDescription = "Habitació",
                modifier = Modifier.size(48.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(res.hotelName, style = MaterialTheme.typography.bodyLarge)
                Text(res.roomType,  style = MaterialTheme.typography.bodyMedium)
                Text("${res.startDate} → ${res.endDate}", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onCancel) {
                Icon(Icons.Default.Delete, contentDescription = "Cancel·la")
            }
        }
    }
}
