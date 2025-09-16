package com.dmmeta.nolapp.presentation.theme

import androidx.compose.runtime.Composable

@Composable
expect fun AppTheme(isDarkMode: Boolean, content: @Composable (() -> Unit))