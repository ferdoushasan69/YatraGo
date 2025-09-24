package com.dmmeta.nolapp.presentation.screen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nolapp.composeapp.generated.resources.Res
import nolapp.composeapp.generated.resources.close
import nolapp.composeapp.generated.resources.search
import org.jetbrains.compose.resources.painterResource

@Composable
fun SearchListTile(
    modifier: Modifier = Modifier,
    locationName: String,
    dateInfo: String,
    onCancel: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(Res.drawable.search),
            modifier = Modifier.size(24.dp),
            contentDescription = null
        )
        Spacer(Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
            Text(text = locationName, style = MaterialTheme.typography.titleMedium)
            Text(text = dateInfo, style = MaterialTheme.typography.titleSmall)
        }
        IconButton(onClick = onCancel) {
            Icon(
                painterResource(Res.drawable.close),
                modifier = Modifier.size(24.dp),
                contentDescription = null
            )
        }
    }
}