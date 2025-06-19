package com.example.waygo.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.waygo.data.remote.model.Hotel
import com.example.waygo.data.remote.model.Room

@Composable
fun HotelList(hotels: List<Hotel>, onClick: (Hotel) -> Unit) {
    Log.d("HotelList", "Renderitzant ${hotels.size} hotels")

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(hotels) { hotel ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(hotel) } // 👉 Fa clic al hotel
            ) {
                Column(modifier = Modifier.padding(12.dp)) {

                    Image(
                        painter = rememberAsyncImagePainter(hotel.imageUrl),
                        contentDescription = "Imatge de l'hotel",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(hotel.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(hotel.address ?: "Adreça desconeguda")
                    Text("⭐ ${hotel.rating} · Des de ${hotel.rooms.minOfOrNull { it.price } ?: "-"}€")

                    Spacer(modifier = Modifier.height(8.dp))

                    hotel.rooms.take(3).forEach { room ->
                        RoomItem(room = room) // no cal reservar des d'aquí
                    }
                }
            }
        }
    }
}


@Composable
fun RoomItem(room: Room) {
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

    }
}
