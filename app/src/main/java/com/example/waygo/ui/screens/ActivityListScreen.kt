package com.example.waygo.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.waygo.viewmodel.ActivityViewModel
import com.example.waygo.viewmodel.TripViewModel
import com.example.waygo.models.Itinerary
import androidx.compose.foundation.lazy.items



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityListScreen(
    tripId: String,
    navController: NavController,
    tripViewModel: TripViewModel,
    activityViewModel: ActivityViewModel // 👈 Afegeix aquest nou paràmetre
)
 {

     LaunchedEffect(tripId) {
         activityViewModel // ✅ Només si vols fer una recàrrega
     }




     val activities = activityViewModel.activities.collectAsState().value
     val filteredActivities = activities.filter { it.tripId == tripId }


     Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Activitats del viatge") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Tornar")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("add_activity/$tripId")
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Afegir activitat")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (filteredActivities.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Text(
                    "No hi ha activitats afegides.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {

            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredActivities, key = { it.id }) { activity: Itinerary ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(activity.title, style = MaterialTheme.typography.titleLarge)
                            Text(activity.description)
                            Text("📅 Dia: ${activity.day}")
                            Text("🕒 Hora: ${activity.hour}")

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = {
                                    activityViewModel.deleteActivity(activity.id)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                            ) {
                                Text("Elimina")
                            }

                            Button(
                                onClick = {
                                    navController.navigate("edit_activity/${activity.id}/$tripId")
                                }
                            ) {
                                Text("Editar")
                            }
                        }
                    }
                }
            }


        }
    }

 }

