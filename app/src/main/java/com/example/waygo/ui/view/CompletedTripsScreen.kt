package com.example.waygo.ui.view

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.waygo.domain.model.Trip
import com.example.waygo.ui.viewmodel.TripViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toSet
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.get

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompletedTripsScreen(
    navController: NavController,
    viewModel: TripViewModel = hiltViewModel()
) {
    val trips = viewModel.trips

    val uniqueDestinations = trips.map { it.destination }.toSet()

    var showDialog by remember { mutableStateOf(false) }
    var editingTrip by remember { mutableStateOf<Trip?>(null) }

    var destination by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf(Date()) }
    var endDate by remember { mutableStateOf(Date()) }
    var budget by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var isFavorite by remember { mutableStateOf(false) }
    var coverImageUrl by remember { mutableStateOf("") }

    val context = LocalContext.current

    LaunchedEffect((editingTrip?.id)) {
        if (editingTrip != null) {
            destination = editingTrip?.destination.orEmpty()
            startDate = editingTrip?.startDate ?: Date()
            endDate = editingTrip?.endDate ?: Date()
            budget = editingTrip?.budget.toString()
            notes = editingTrip?.notes.orEmpty()
            isFavorite = editingTrip?.isFavorite ?: false
            coverImageUrl = editingTrip?.coverImageUrl.orEmpty()
        } else {
            destination = ""
            startDate = Date()
            endDate = Date()
            budget = ""
            notes = ""
            isFavorite = false
            coverImageUrl = ""
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showDialog = true
                    editingTrip = null
                }, modifier = Modifier.padding(16.dp),
                containerColor = Color.Black,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
                .padding(16.dp)
        ) {
            if (trips.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.no_completed_trips), fontSize = 18.sp, color = Color.Gray)
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(trips.size) { index ->
                        CompletedTripCard(
                            trip = trips[index],
                            Log.d("IdNumber", "TripId: ${trips[index].id}"),
                            onClick = { navController.navigate("itinerary/${trips[index].id}") },
                            onDelete = { viewModel.deleteTrip(trips[index].id) },
                            onEdit = {
                                editingTrip = trips[index]
                                showDialog = true
                            }
                        )
                    }
                }
            }
        }
        if (showDialog) {
            var destinationError by remember { mutableStateOf(false) }
            var budgetError by remember { mutableStateOf(false) }
            var startDateError by remember { mutableStateOf(false) }
            var endDateError by remember { mutableStateOf(false) }
            var duplicateDestinationError by remember { mutableStateOf(false) }

            AlertDialog(
                onDismissRequest = { showDialog = false },
                properties = DialogProperties(dismissOnClickOutside = false),
                containerColor = Color.White,
                title = {
                    Text(
                        if (editingTrip == null) stringResource(R.string.add_new_trip)else stringResource(R.string.edit_trip),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                },
                text = {
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, shape = RoundedCornerShape(12.dp))
                            .padding(16.dp)
                    ){
                        OutlinedTextField(
                            value = destination,
                            onValueChange = {
                                destination = it
                                destinationError = it.isBlank()
                                duplicateDestinationError = !editingTrip?.destination.equals(it, true) && uniqueDestinations.contains(it)
                            },
                            label = { Text(stringResource(R.string.destination), color = Color.Black) },
                            isError = destinationError,
                            textStyle = TextStyle(color = Color.Black),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Black,
                                cursorColor = Color.Black,
                                focusedLabelColor = Color.Black,
                                unfocusedLabelColor = Color.Gray
                            )
                        )
                        if (destinationError) Text(
                            stringResource(R.string.destination_required),
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                        if (duplicateDestinationError) Text(
                            stringResource(R.string.destination_duplicate),
                            color = Color.Red,
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Column (
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(8.dp)
                        ) {
                            Button(onClick = {
                                showDatePicker(context, startDate) {
                                    startDate = it
                                    startDateError = it.before(Date())
                                    endDateError = endDate.before(startDate)
                                }},
                                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                            ) {
                                Text("${stringResource(R.string.start_date)}: ${formatDate(startDate)}", color = Color.Black)
                            }
                            if (startDateError) Text(
                                stringResource(R.string.start_date_future),
                                color = Color.Red,
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = {
                                showDatePicker(context, endDate) {
                                    endDate = it
                                    endDateError = it.before(startDate)
                                } },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                            ) {
                                Text("${stringResource(R.string.end_date)}: ${formatDate(endDate)}", color = Color.Black)
                            }
                            if(endDateError) Text(
                                stringResource(R.string.end_date_future),
                                color = Color.Red,
                                fontSize = 12.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = budget,
                            onValueChange = {
                                budget = it
                                budgetError = it.toDoubleOrNull() == null
                            },
                            label = { Text(stringResource(R.string.budget), color = Color.Black) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = budgetError,
                            textStyle = TextStyle(color = Color.Black),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Black,
                                cursorColor = Color.Black,
                                focusedLabelColor = Color.Black,
                                unfocusedLabelColor = Color.Gray
                            )
                        )
                        if (budgetError) Text(
                            stringResource(R.string.enter_valid_budget),
                            color = Color.Red,
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = notes,
                            onValueChange = { notes = it },
                            label = { Text(stringResource(R.string.notes), color = Color.Black) },
                            textStyle = TextStyle(color = Color.Black),
                            shape = RoundedCornerShape(16.dp),
                            maxLines = 3,
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Black,
                                cursorColor = Color.Black,
                                focusedLabelColor = Color.Black,
                                unfocusedLabelColor = Color.Gray
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(stringResource(R.string.mark_as_favorite), color = Color.Black)
                            Spacer(modifier = Modifier.width(8.dp))
                            Switch(checked = isFavorite,
                                onCheckedChange = { isFavorite = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.Gray,
                                    uncheckedThumbColor = Color.Gray,
                                    disabledUncheckedThumbColor = Color.Gray,
                                    checkedTrackColor = Color.DarkGray,
                                    uncheckedTrackColor = Color.LightGray,
                                    disabledCheckedTrackColor = Color.Gray,
                                    disabledUncheckedTrackColor = Color.LightGray
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = coverImageUrl,
                            onValueChange = { coverImageUrl = it },
                            label = { Text(stringResource(R.string.image_url), color = Color.Black) },
                            textStyle = TextStyle(color = Color.Black),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Black,
                                cursorColor = Color.Black,
                                focusedLabelColor = Color.Black,
                                unfocusedLabelColor = Color.Gray
                            )
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            destinationError = destination.isBlank()
                            duplicateDestinationError = !editingTrip?.destination.equals(destination, true) && uniqueDestinations.contains(destination)
                            budgetError = budget.toDoubleOrNull() == null
                            startDateError = startDate.before(Date())
                            endDateError = endDate.before(startDate)

                            if (!destinationError && !duplicateDestinationError && !budgetError && !startDateError && !endDateError) {
                                if (editingTrip == null) {
                                    viewModel.addTrip(
                                        destination = destination,
                                        startDate = startDate,
                                        endDate = endDate,
                                        budget = budget.toDoubleOrNull() ?: 0.0,
                                        notes = notes,
                                        isFavorite = isFavorite,
                                        coverImageUrl = coverImageUrl
                                    )
                                } else {
                                    viewModel.updateTrip(
                                        editingTrip!!.copy(
                                            destination = destination,
                                            startDate = startDate,
                                            endDate = endDate,
                                            budget = budget.toDoubleOrNull() ?: 0.0,
                                            notes = notes,
                                            isFavorite = isFavorite,
                                            coverImageUrl = coverImageUrl
                                        )
                                    )
                                }
                                showDialog = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    ) {
                        Text(stringResource(R.string.save), color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) { Text(stringResource(R.string.cancel), color = Color.Black) }
                }
            )
        }
    }
}

@Composable
fun CompletedTripCard(
    trip: Trip,
    onClick1: Int,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            trip.coverImageUrl?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Trip Cover",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = trip.destination,
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(4.dp))

                // Fechas del viaje
                Text(
                    text = "${formatDate(trip.startDate)} - ${formatDate(trip.endDate)}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Presupuesto del viaje
                Text(
                    text = "💰 ${trip.budget} €",
                    fontSize = 16.sp,
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.DarkGray)
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                    }
                }
            }
        }
    }
}

fun formatDate(date: Date): String {
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return formatter.format(date)
}

fun showDatePicker(context: Context, initialDate: Date, onDateSelected: (Date) -> Unit) {
    val calendar = Calendar.getInstance().apply { time = initialDate }
    DatePickerDialog(
        context, { _, year, month, day ->
            calendar.set(year, month, day)
            onDateSelected(calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}