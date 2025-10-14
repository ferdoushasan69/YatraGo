package com.dmmeta.choloja.presentation.screen.component

import androidx.compose.runtime.Composable

// commonMain
@Composable
expect fun DateRangePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: (Long, Long) -> Unit
)
