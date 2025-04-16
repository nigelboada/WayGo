package com.example.waygo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.waygo.viewmodel.ItineraryViewModel
import com.example.waygo.viewmodel.ItineraryViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditItineraryItemScreen(
    navController: NavController,
    tripId: String,
    activityId: String
) {
    val viewModel: ItineraryViewModel = viewModel(
        factory = ItineraryViewModelFactory(tripId)
    )

    val activity = viewModel.getActivityById(activityId)

    var name by remember { mutableStateOf(activity?.title ?: "") }
    var description by remember { mutableStateOf(activity?.description ?: "") }
    var time by remember { mutableStateOf(activity?.time ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Editar Activitat") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nom") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripció") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = time,
                onValueChange = { time = it },
                label = { Text("Hora") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (activity != null && name.isNotBlank()) {
                        viewModel.updateActivity(
                            activity.copy(
                                title = name,
                                description = description,
                                time = time
                            )
                        )
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Desa canvis")
            }
        }
    }
}
