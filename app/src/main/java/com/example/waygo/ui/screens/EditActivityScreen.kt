package com.example.waygo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.waygo.viewmodel.ActivityViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditActivityScreen(
    navController: NavController,
    activityId: String,
    tripId: String,
    viewModel: ActivityViewModel = viewModel()
)
 {
    val activity = viewModel.getActivityById(activityId)

    if (activity == null) {
        // Mentre no es troba, mostrem pantalla de càrrega o un missatge
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Carregant activitat...")
        }
        return
    }
    var title by remember { mutableStateOf(activity.title) }
    var description by remember { mutableStateOf(activity.description) }
    var time by remember { mutableStateOf(activity.time) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar activitat") },
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
                    activity.let {
                        viewModel.updateActivity(it.copy(
                            title = title,
                            description = description,
                            time = time
                        ))
                    }
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Desar canvis")
            }
        }
    }
}
