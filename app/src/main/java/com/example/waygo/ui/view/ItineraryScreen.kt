package com.example.waygo.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.waygo.domain.model.ItineraryItem
import com.example.waygo.ui.components.DatePickerDialogComponent
import com.example.waygo.ui.viewmodel.AuthViewModel
import com.example.waygo.ui.viewmodel.ItineraryViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.get

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItineraryScreen(navController: NavController, tripId: Int, tripName: String, viewModel: ItineraryViewModel = hiltViewModel(), authViewModel: AuthViewModel) {
    val itineraryItems by viewModel.getItineraryItems(tripId).collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var activityName by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var activityDate by remember { mutableStateOf(Date()) }
    var editingItem by remember { mutableStateOf<ItineraryItem?>(null) }

    var nameError by remember { mutableStateOf(false) }
    var locationError by remember { mutableStateOf(false) }
    var dateError by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.itinerary_title),
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp),
                    color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back",
                            tint = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
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
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .background(Color.White)
        ) {
            if (itineraryItems.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.no_itinerary_items),
                        fontSize = 18.sp,
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(itineraryItems.size) { index ->
                        ItineraryItemCard(
                            item = itineraryItems[index],
                            onEdit = {
                                editingItem = it
                                activityName = it.description
                                location = it.location
                                activityDate = it.date
                                showDialog = true
                            },
                            onDelete = { viewModel.deleteItineraryItem(it.id) }
                        )
                    }
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                containerColor = Color.White,
                onDismissRequest = {
                    showDialog = false
                    editingItem = null
                },
                title = { Text(if (editingItem == null) stringResource(R.string.add_itinerary_item) else stringResource(R.string.edit_itinerary_item),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                ) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedTextField(
                            value = activityName,
                            onValueChange = { activityName = it },
                            label = { Text(stringResource(R.string.activity_name), color = Color.Black) },
                            textStyle = LocalTextStyle.current.copy(color = Color.Black),
                            isError = nameError,
                            shape = RoundedCornerShape(12.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Black
                            )
                        )
                        if(nameError) {
                            Text(stringResource(R.string.field_required), color = Color.Red, fontSize = 12.sp)
                        }
                        OutlinedTextField(
                            value = location,
                            onValueChange = { location = it
                            },
                            label = { Text(stringResource(R.string.location), color = Color.Black) },
                            isError = locationError,
                            shape = RoundedCornerShape(12.dp),
                            textStyle = LocalTextStyle.current.copy(color = Color.Black),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Black
                            )
                        )
                        if (locationError) {
                            Text(stringResource(R.string.field_required), color = Color.Red, fontSize = 12.sp)
                        }
                        Button(
                            onClick = { showDatePicker = true },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                        ) {
                            Text("${stringResource(R.string.select_date)}: ${SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(activityDate)}",
                                color = Color.White)
                        }
                        if (dateError) {
                            Text(stringResource(R.string.date_in_past), color = Color.Red, fontSize = 12.sp)
                        }

                        if (showDatePicker) {
                            DatePickerDialogComponent(
                                initialDate = activityDate,
                                onDateSelected = { selectedDate ->
                                    if (selectedDate.before(Date())) {
                                        dateError = true
                                    } else {
                                        activityDate = selectedDate
                                        dateError = false
                                    }
                                    showDatePicker = false
                                },
                                onDismiss = { showDatePicker = false }
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            nameError = activityName.isBlank()
                            locationError = location.isBlank()
                            dateError = activityDate.before(Date())
                            if (!nameError && !locationError && !dateError) {
                                if (editingItem == null) {
                                    viewModel.addItineraryItem(
                                        ItineraryItem(
                                            id = 0,
                                            tripId = tripId,
                                            description = activityName,
                                            date = activityDate,
                                            location = location
                                        ),
                                    )
                                } else {
                                    viewModel.updateItineraryItem(
                                        editingItem!!.copy(
                                            description = activityName,
                                            date = activityDate,
                                            location = location
                                        )
                                    )
                                }
                                showDialog = false
                                editingItem = null
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    ) {
                        Text(if (editingItem == null) stringResource(R.string.save) else stringResource(R.string.update), color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDialog = false
                        editingItem = null
                    }) { Text(stringResource(R.string.cancel), color = Color.Black) }
                }
            )
        }
    }
}
@Composable
fun ItineraryItemCard(item: ItineraryItem, onEdit: (ItineraryItem) -> Unit, onDelete: (ItineraryItem) -> Unit) {
    val formatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = item.description,
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))


            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Place, contentDescription = "Location", tint = Color.Black)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = item.location, fontSize = 14.sp, color = Color.Black)
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.DateRange, contentDescription = "Date", tint = Color.Black)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = formatter.format(item.date), fontSize = 14.sp, color = Color.Black)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onEdit(item) }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.Black)
                }
                IconButton(onClick = { onDelete(item) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                }
            }
        }
    }
}
