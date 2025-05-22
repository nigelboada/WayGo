package com.example.waygo.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.waygo.domain.model.Trip
import com.example.waygo.domain.repository.TripRepository2

@Composable
fun FavoriteTripsScreen() {
    val favoriteTrips = TripRepository2.trips.take(2)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(16.dp)
    ) {
        if (favoriteTrips.isEmpty()) {
            EmptyMessage(stringResource(R.string.no_favorite_trips))
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(favoriteTrips) { trip ->
                    FavoriteTripCard(trip)
                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(vertical = 6.dp))
                }
            }
        }
    }
}

@Composable
fun FavoriteTripCard(trip: Trip) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
    ) {
        Column {
            Image(
                painter = painterResource(R.drawable.ic_launcher),
                contentDescription = trip.destination,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = trip.destination, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(text = "${trip.startDate} - ${trip.endDate}", color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Icon(Icons.Filled.Favorite, contentDescription = "Favorite", tint = Color.Red)
            }
        }
    }
}

@Composable
fun EmptyMessage(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(message, style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
    }
}