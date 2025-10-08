package com.dmmeta.yatrago.presentation.screen.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dmmeta.yatrago.presentation.theme.PrimaryColor

@Composable
fun CustomLocationItem(
    locationName: String,
    isDetails: Boolean = false,
    buttonText: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .shoBorder(isDetails)
            .fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            text = locationName,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        TextButton(onClick = onClick) {
            Text(buttonText, color = PrimaryColor, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
private fun Modifier.shoBorder(isDetails: Boolean): Modifier {
    return if (isDetails) {
        border(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)
    } else {
        Modifier
    }
}