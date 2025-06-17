package com.example.waygo.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text


@Composable
fun DropdownMenuCitySelector(
    selectedCity: String,
    onCitySelected: (String) -> Unit
) {
    val cities = listOf("Barcelona", "Londres", "Paris")
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text("Select City", style = MaterialTheme.typography.labelLarge)
        Box {
            OutlinedButton(onClick = { expanded = true }) {
                Text(selectedCity)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                cities.forEach { city ->
                    DropdownMenuItem(
                        text = { Text(city) },
                        onClick = {
                            onCitySelected(city)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
