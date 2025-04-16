package com.example.waygo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.waygo.viewmodel.ActivityViewModel
import com.example.waygo.models.ItineraryItem
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddActivityScreen(
    navController: NavController,
    tripId: String,
    viewModel: ActivityViewModel
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Afegir activitat") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Tornar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Títol") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripció") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = time,
                onValueChange = { time = it },
                label = { Text("Hora (ex: 10:00)") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val newActivity = ItineraryItem(
                        id = UUID.randomUUID().toString(),
                        tripId = tripId,
                        title = title,
                        description = description,
                        time = time
                    )
                    viewModel.addActivity(newActivity)
                    navController.popBackStack() // Tornem a la llista
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Afegir activitat")
            }
        }
    }
}
