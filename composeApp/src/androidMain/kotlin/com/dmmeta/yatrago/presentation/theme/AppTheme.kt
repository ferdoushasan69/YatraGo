package com.dmmeta.yatrago.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
actual fun AppTheme(isDarkMode : Boolean,content : @Composable (()-> Unit)){

    CompositionLocalProvider{
        MaterialTheme(
            colorScheme = if (isDarkMode) darkColorScheme() else lightColorScheme(),
            typography = MaterialTheme.typography,
            content = content,
        )
    }
}