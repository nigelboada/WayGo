package com.example.waygo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Home") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "Welcome to WayGo!", style = MaterialTheme.typography.headlineMedium)
            Button(onClick = { navController.navigate("about") }) {
                Text("About")
            }
            Button(onClick = { navController.navigate("itinerary") }) {
                Text("Itinerary")
            }
            Button(onClick = { navController.navigate("profile") }) {
                Text("Profile")
            }
        }
    }
}
