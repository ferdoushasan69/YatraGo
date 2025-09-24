package com.dmmeta.nolapp.presentation.screen.component

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String? = "", modifier: Modifier = Modifier,
    navIcon: @Composable (() -> Unit)? = null,
    topBarAction: @Composable (() -> Unit)? = null,
    isHome: Boolean = false,
    textStyle: TextStyle = TextStyle()
) {
    TopAppBar(
        windowInsets = WindowInsets(0),
        title = {
            Text(
                text = title ?: "", textAlign = if (isHome) TextAlign.Center else TextAlign.Start,
                color = Color.White,
                style = textStyle,
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        navigationIcon = {
            navIcon?.invoke()
        },
        actions = {
            topBarAction?.invoke()
        },
    )
}