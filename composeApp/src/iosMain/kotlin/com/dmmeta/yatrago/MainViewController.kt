package com.dmmeta.yatrago

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController { App(isDarkMode = isSystemInDarkTheme()) }