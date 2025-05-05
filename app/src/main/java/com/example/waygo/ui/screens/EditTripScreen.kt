package com.example.waygo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.waygo.viewmodel.TripViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTripScreen(
    navController: NavController,
    tripId: String,
    viewModel: TripViewModel
) {
    // Cridar a getTripById per carregar el viatge
    LaunchedEffect(tripId) {
        viewModel.getTripById(tripId)
    }


    // Obtenir el viatge a editar
    val trip by viewModel.trip.collectAsState(initial = null)

    // Si el viatge és null, mostrar un carregant o alguna cosa fins que el viatge arribi
    if (trip == null) {
        // Mostrar un spinner o algun altre indicador de càrrega
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        return
    }


    // Inicialitzar l'estat amb els valors del viatge
    var title by remember { mutableStateOf(trip!!.title) }
    var description by remember { mutableStateOf(trip!!.description) }
    var location by remember { mutableStateOf(trip!!.location) }
    var startDate by remember { mutableStateOf(trip!!.startDate) }
    var endDate by remember { mutableStateOf(trip!!.endDate) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar viatge") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Tornar")
                    }
                },
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
                value = title,
                onValueChange = { title = it },
                label = { Text("Títol del viatge") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripció") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Ubicació") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = startDate,
                onValueChange = { startDate = it },
                label = { Text("Data inici (dd/MM/yyyy)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = endDate,
                onValueChange = { endDate = it },
                label = { Text("Data fi (dd/MM/yyyy)") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val today = Calendar.getInstance().time

                    try {
                        val start = dateFormat.parse(startDate)
                        val end = dateFormat.parse(endDate)

                        when {
                            title.isBlank() || description.isBlank() || location.isBlank() || startDate.isBlank() || endDate.isBlank() -> {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Tots els camps són obligatoris")
                                }
                            }
                            start == null || end == null -> {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Les dates no poden ser buides")
                                }
                            }
                            start.before(today) -> {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("La data d'inici ha de ser avui o més endavant")
                                }
                            }
                            end.before(start) -> {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("La data de finalització ha de ser igual o posterior a la d'inici")
                                }
                            }
                            else -> {
                                viewModel.updateTrip(
                                    tripId = tripId,
                                    title = title,
                                    description = description,
                                    location = location,
                                    startDate = startDate,
                                    endDate = endDate
                                )
                                navController.popBackStack()
                            }
                        }
                    } catch (_: Exception) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Format de data incorrecte (usa dd/MM/yyyy)")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar canvis")
            }
        }
    }
}