package com.example.waygo.ui.view.activity

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.waygo.ui.viewmodel.ActivityViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditActivityScreen(
    navController: NavController,
    activityId: String,
    tripId: String,
    availableDays: List<String>, // Dies del viatge passats com a paràmetre
    activityViewModel: ActivityViewModel = viewModel()
) {
    val activities by activityViewModel.activities.collectAsState()
    val activity = activities.find { it.id == activityId }

    if (activity == null) {
        // Mostrar carregant
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        return
    }

    var name by remember { mutableStateOf(activity.title) }
    var description by remember { mutableStateOf(activity.description) }
    var selectedDay by remember { mutableStateOf(activity.day) }
    var hour by remember { mutableStateOf(activity.hour) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Activitat") },
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

            // Dropdown per seleccionar el dia
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedDay,
                    onValueChange = {},
                    label = { Text("Dia del viatge") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null
                        )
                    }
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    availableDays.forEach { day ->
                        DropdownMenuItem(
                            text = { Text(day) },
                            onClick = {
                                selectedDay = day
                                expanded = false
                            }
                        )
                    }
                }
            }

            // TextField per l'hora
            OutlinedTextField(
                value = hour,
                onValueChange = { hour = it },
                label = { Text("Hora de l'activitat (HH:mm)") },
                isError = !isValidHourFormat(hour),
                modifier = Modifier.fillMaxWidth()
            )
            if (!isValidHourFormat(hour)) {
                Text("Format d'hora incorrecte", color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = {
                    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                    val today = Calendar.getInstance().time

                    try {
                        val activityHourParsed = dateFormat.parse(hour)

                        when {
                            name.isBlank() || description.isBlank() || selectedDay.isBlank() || hour.isBlank() -> {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Tots els camps són obligatoris")
                                }
                            }
                            activityHourParsed == null -> {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("La hora de l'activitat és incorrecta")
                                }
                            }
                            else -> {
                                // Actualitzar l'activitat
                                activityViewModel.updateActivity(
                                    updated = activity.copy(
                                        title = name,
                                        description = description,
                                        day = selectedDay,
                                        hour = hour
                                    )
                                )
                                navController.popBackStack()
                            }
                        }
                    } catch (e: Exception) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Format d'hora incorrecte (usa HH:mm)")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Activitat")
            }
        }
    }
}

fun isValidHourFormat(hour: String): Boolean {
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    timeFormat.isLenient = false
    return try {
        timeFormat.parse(hour)
        true
    } catch (e: Exception) {
        false
    }
}