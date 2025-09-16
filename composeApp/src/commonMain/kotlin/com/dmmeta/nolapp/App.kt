package com.dmmeta.nolapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.dmmeta.nolapp.presentation.navigation.AppNavigation
import com.dmmeta.nolapp.presentation.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    isDarkMode: Boolean
) {
    val navController = rememberNavController()

    AppTheme(isDarkMode = isDarkMode) {
        AppNavigation(navController)
    }
}