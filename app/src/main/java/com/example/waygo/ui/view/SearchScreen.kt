package com.example.waygo.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.waygo.ui.viewmodel.BookViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    vm: BookViewModel = hiltViewModel()
) {
    val ui by vm.uiState.collectAsState()

    Column(Modifier.padding(16.dp)) {

        Text(
            text = "Search Hotels",
            style = MaterialTheme.typography.headlineMedium.copy(color = Color.Black),
            modifier = Modifier.padding(bottom = 16.dp),
        )

        /* ───── Selector de ciudad ───── */
        ExposedDropdownMenuBox(
            expanded = ui.cityMenu,
            onExpandedChange = { vm.toggleCityMenu() }
        ) {
            TextField(
                value = ui.city,
                onValueChange = {},
                readOnly = true,
                label = { Text("City", color = Color.Black) },
                leadingIcon = { Icon(Icons.Default.Place, null, tint = Color.Black) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF007AFF),
                    unfocusedIndicatorColor = Color.LightGray,
                    disabledIndicatorColor = Color.Transparent,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    disabledTextColor = Color.Black,

                    )
            )

            ExposedDropdownMenu(
                expanded = ui.cityMenu,
                onDismissRequest = { vm.toggleCityMenu() }
            ) {
                listOf("Barcelona", "Paris", "Londres").forEach { c ->
                    DropdownMenuItem(
                        text = { Text(c, color = Color.Black) },
                        onClick = { vm.selectCity(c) }
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))
        DateField("Start Date", ui.startDate) { vm.pickStart(it) }
        Spacer(Modifier.height(8.dp))
        DateField("End Date", ui.endDate) { vm.pickEnd(it) }

        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { vm.search() },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(10.dp),
            border = ButtonDefaults.outlinedButtonBorder
        ) {
            Text("Search Hotels")
        }

        Spacer(Modifier.height(16.dp))

        if (ui.loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            if (ui.hotels.isNotEmpty()) {
                HotelList(ui.hotels) { hotel ->
                    navController.navigate(
                        Screen.Hotel.create(
                            hotel.id,
                            vm.groupId,
                            ui.startDate.toString(),
                            ui.endDate.toString()
                        )
                    )
                }
            }

            if (ui.message != null) {
                Text(
                    text = ui.message!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}
