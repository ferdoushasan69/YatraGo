package com.dmmeta.nolapp.presentation.screen.component

import androidx.compose.runtime.Composable

// commonMain
@Composable
expect fun DateRangePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: (Long, Long) -> Unit
)
