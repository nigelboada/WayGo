package com.example.waygo.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.waygo.data.remote.model.Hotel
import com.example.waygo.ui.view.base

@Composable
fun HotelList(hotels: List<Hotel>, onClick: (Hotel) -> Unit) {

    Log.d("HotelList", "Renderitzant ${hotels.size} hotels")

    LazyColumn {
        items(hotels) { hotel ->
            Log.d("UI", "Dibuixant hotel: ${hotel.name}")

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .clickable { onClick(hotel) }
            ) {
                Column {
                    Row(Modifier.height(120.dp)) {
                        Image(
                            painter = rememberAsyncImagePainter(base + hotel.imageUrl),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.width(120.dp).fillMaxHeight()
                        )
                        Column(Modifier.padding(8.dp)) {
                            Text(hotel.name, fontWeight = FontWeight.Bold)
                            Text(hotel.address)
                            Spacer(Modifier.weight(1f))
                            Text("From ${hotel.rooms.minOfOrNull { it.price } ?: "-"}€", fontWeight = FontWeight.SemiBold)
                        }
                    }
                    // 🔽 Afegeix les habitacions
                    hotel.rooms.forEach { room ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                        ) {
                            if (room.images.isNotEmpty()) {
                                Image(
                                    painter = rememberAsyncImagePainter(base + room.images.first()),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .width(60.dp)
                                        .height(60.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }

                            Column {
                                Text("${room.roomType}: ${room.price}€", style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }

                }
            }
        }
    }
}

