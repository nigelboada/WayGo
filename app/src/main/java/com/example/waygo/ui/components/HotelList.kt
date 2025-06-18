package com.example.waygo.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.waygo.data.remote.model.Hotel
import com.example.waygo.data.remote.model.Room
import com.example.waygo.ui.view.base

@Composable
fun HotelList(hotels: List<Hotel>, onClick: (Hotel, Room) -> Unit) {
    Log.d("HotelList", "Renderitzant ${hotels.size} hotels")

    LazyColumn {
        items(hotels) { hotel ->
            Log.d("IMAGE_DEBUG", "Hotel: ${hotel.name}, imageUrl: ${hotel.imageUrl}")

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    // 📸 Imatge gran de l'hotel
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = base + hotel.imageUrl,
                            onState = { state ->
                                if (state is AsyncImagePainter.State.Error) {
                                    Log.e("IMAGE_ERROR", "No es pot carregar: ${hotel.imageUrl}")
                                    Log.e("IMAGE_ERROR", "URL fallida: ${base + hotel.imageUrl}")

                                }
                            }
                        ),
                        contentDescription = "Imatge de l'hotel",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(hotel.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text(hotel.address ?: "Adreça desconeguda", style = MaterialTheme.typography.bodyMedium)
                    Text("⭐ ${hotel.rating} · Des de ${hotel.rooms.minOfOrNull { it.price } ?: "-"}€", style = MaterialTheme.typography.bodySmall)

                    Spacer(modifier = Modifier.height(8.dp))

                    // 🛏 Habitacions (fins a 3)
                    hotel.rooms.take(3).forEach { room ->
                        RoomItem(room = room) {
                            onClick(hotel, room)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RoomItem(room: Room, onReserve: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text("• ${room.roomType}", fontWeight = FontWeight.SemiBold)
            Text("${room.price} €", style = MaterialTheme.typography.bodySmall)
        }

        Button(onClick = onReserve) {
            Text("Reservar")
        }
    }
}
