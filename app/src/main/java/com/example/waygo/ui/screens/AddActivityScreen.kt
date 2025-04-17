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
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddActivityScreen(
    navController: NavController,
    tripId: String,
    activityViewModel: ActivityViewModel = viewModel()  // Utilitzem ActivityViewModel aquí
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
//    var location by remember { mutableStateOf("") }
    var activityDate by remember { mutableStateOf("") }

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

//            OutlinedTextField(
//                value = location,
//                onValueChange = { location = it },
//                label = { Text("Ubicació") },
//                modifier = Modifier.fillMaxWidth()
//            )

            OutlinedTextField(
                value = activityDate,
                onValueChange = { activityDate = it },
                label = { Text("Data de l'activitat (dd/MM/yyyy)") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val today = Calendar.getInstance().time

                    try {
                        val activityDateParsed = dateFormat.parse(activityDate)

                        when {
                            name.isBlank() || description.isBlank() || activityDate.isBlank() -> {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Tots els camps són obligatoris")
                                }
                            }
                            activityDateParsed == null -> {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("La data de l'activitat és incorrecta")
                                }
                            }
                            activityDateParsed.before(today) -> {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("La data de l'activitat ha de ser avui o més endavant")
                                }
                            }
                            else -> {
                                // Crear l'activitat
                                val newActivity = Activity(
                                    tripId = tripId,
                                    title = name,
                                    description = description,
                                    time = activityDate
                                )

                                // Afegir l'activitat al ActivityViewModel
                                activityViewModel.addActivity(newActivity)

                                // Tornar enrere després d'afegir
                                navController.popBackStack()
                            }
                        }
                    } catch (e: Exception) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Format de data incorrecte (usa dd/MM/yyyy)")
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
