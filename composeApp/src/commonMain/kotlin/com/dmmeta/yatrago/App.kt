package com.dmmeta.yatrago


import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.dmmeta.yatrago.presentation.navigation.AppNavigation
import com.dmmeta.yatrago.presentation.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    isDarkMode: Boolean
) {

    val navHostController = rememberNavController()
    AppTheme(isDarkMode = isDarkMode) {
        AppNavigation(navController = navHostController)
    }
}