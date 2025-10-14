package com.dmmeta.choloja.presentation.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Discount
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CustomOfferButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    offerText: String
) {


    Row(
        modifier = modifier
            .padding(8.dp)
            .background(
                color = MaterialTheme.colorScheme.outlineVariant.copy(.2f),
                shape = RoundedCornerShape(
                    12.dp
                )
            )
            .padding(
                8.dp
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier.size(25.dp)
        )
        Spacer(Modifier.width(4.dp))
        Text(text = offerText, style = MaterialTheme.typography.titleMedium)
    }
}

@Preview
@Composable
fun PreviewButton() {
    CustomOfferButton(
        modifier = Modifier.fillMaxWidth(),
        imageVector = Icons.Default.Discount,
        offerText = "최대3만쿠폰"
    )
}