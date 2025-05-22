package com.example.waygo.ui.components

import android.app.DatePickerDialog
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import java.util.*

@Composable
fun DatePickerDialogComponent(
    initialDate: Date,
    onDateSelected: (Date) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance().apply { time = initialDate }

    // Show DatePickerDialog only when required
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        LaunchedEffect(context) {
            val datePickerDialog = DatePickerDialog(
                context,
                { _, year, month, day ->
                    calendar.set(year, month, day)
                    onDateSelected(calendar.time)
                    showDialog = false
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.setOnDismissListener { onDismiss() }
            datePickerDialog.show()
        }
    }
}
