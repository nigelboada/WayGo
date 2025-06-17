package com.example.waygo.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DatePickerField(
    label: String,
    date: String,
    onDateChange: (String) -> Unit
) {
    OutlinedTextField(
        value = date,
        onValueChange = { onDateChange(it) },
        label = { Text(label) },
        placeholder = { Text("YYYY-MM-DD") },
        modifier = Modifier.fillMaxWidth()
    )
}
