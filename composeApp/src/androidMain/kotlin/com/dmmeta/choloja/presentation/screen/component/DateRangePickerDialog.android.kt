package com.dmmeta.choloja.presentation.screen.component

import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun DateRangePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: (Long, Long) -> Unit
) {

        val state = rememberDateRangePickerState()
        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(
                    onClick = {
                        val start = state.selectedStartDateMillis ?: return@TextButton
                        val end = state.selectedEndDateMillis ?: return@TextButton
                        onConfirm(start, end)
                        onDismiss()
                    }
                ) { Text("OK") }
            },
            dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
        ) {
            DateRangePicker(state = state)
        }
    }
