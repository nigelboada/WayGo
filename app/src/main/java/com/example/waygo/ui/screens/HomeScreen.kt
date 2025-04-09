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
            TopAppBar(title = { Text("Home Screen") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Welcome to Home Screen")
            // Botons per navegar
            Button(onClick = { navController.navigate("settings") }) {
                Text("Go to Settings")
            }
            Button(onClick = { navController.navigate("itinerary") }) {
                Text("Go to Itinerary")
            }
            Button(onClick = { navController.navigate("profile") }) {
                Text("Go to Profile")
            }
        }
    }
}
