package com.example.waygo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.waygo.viewmodel.ActivityViewModel
import com.example.waygo.models.Activity
import com.example.waygo.viewmodel.TripViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddActivityScreen(
    navController: NavController,
    tripId: String,
    activityViewModel: ActivityViewModel = viewModel(),
    tripViewModel: TripViewModel = viewModel()

// Utilitzem ActivityViewModel aquí
) {

    LaunchedEffect(tripId) {
        tripViewModel.getTripById(tripId)
    }

    val trip by tripViewModel.trip.collectAsState()



    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var activityDay by remember { mutableStateOf("") }
    var activityHour by remember { mutableStateOf("") }


    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Afegir Activitat") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Tornar")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nom de l'activitat") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripció") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = activityDay,
                onValueChange = { activityDay = it },
                label = { Text("Data (dd/MM/yyyy)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = activityHour,
                onValueChange = { activityHour = it },
                label = { Text("Hora (HH:mm)") },
                modifier = Modifier.fillMaxWidth()
            )


            Button(
                onClick = {
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                    timeFormat.isLenient = false  // ❗ Molt important: això desactiva els ajustaments automàtics

                    val today = Calendar.getInstance().time

                    try {
                        val parsedDay = dateFormat.parse(activityDay)
                        val parsedHour = try {
                            timeFormat.parse(activityHour)
                        } catch (e: Exception) {
                            null
                        }

                        if (parsedHour == null || timeFormat.format(parsedHour) != activityHour) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Hora incorrecte (ha d'estar entre 00:00 i 23:59)")
                            }
                            return@Button
                        }

                        val tripStart = trip?.startDate?.let { dateFormat.parse(it) }
                        val tripEnd = trip?.endDate?.let { dateFormat.parse(it) }

                        when {
                            name.isBlank() || description.isBlank() || activityDay.isBlank() || activityHour.isBlank() -> {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Tots els camps són obligatoris")
                                }
                            }
                            parsedDay == null || parsedHour == null -> {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Format incorrecte de data o hora")
                                }
                            }
                            parsedDay.before(today) -> {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("La data ha de ser avui o posterior")
                                }
                            }

                            tripStart != null && tripEnd != null && (parsedDay.before(tripStart) || parsedDay.after(tripEnd)) -> {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("La data ha d'estar dins del període del viatge (${trip?.startDate} - ${trip?.endDate})")
                                }
                            }

                            else -> {
                                val newActivity = Activity(
                                    tripId = tripId,
                                    title = name,
                                    description = description,
                                    day = activityDay,
                                    hour = activityHour
                                )

                                activityViewModel.addActivity(newActivity)
                                navController.popBackStack()
                            }
                        }
                    } catch (e: Exception) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Format de data o hora incorrecte")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Afegir Activitat")
            }
        }
    }
}