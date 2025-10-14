package com.dmmeta.choloja.presentation.screen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.dmmeta.choloja.domain.model.Accommodation


@Composable
fun AccommodationListCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    accommodation: Accommodation
) {
    Row(modifier = modifier.fillMaxWidth().height(210.dp).padding(16.dp).clickable {
        onClick()
    }) {
        AsyncImage(
            model = accommodation.photoUrl,
            contentDescription = null,
            modifier = Modifier.width(120.dp).height(180.dp).clip(RoundedCornerShape(17.dp)),
            contentScale = ContentScale.FillBounds
        )
        Spacer(Modifier.width(8.dp))
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxHeight()
        ) {
            Column(modifier = Modifier) {
                Text(
                    text = "호텔 ・ ${accommodation.stars}성급",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(.5f),
                    modifier = Modifier.padding(start = 4.dp)
                )
                Text(
                    text = accommodation.name,
                    style = MaterialTheme.typography.titleMedium
                        .copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 4.dp)
                )

                TextItem(
                    text = accommodation.cityLine,
                    imageVector = Icons.Default.LocationOn,
                )
            }

            Column(Modifier.padding(horizontal = 16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Text(text = "10%")
                    Spacer(Modifier.width(4.dp))
                    Text(text = accommodation.discount, textDecoration = TextDecoration.LineThrough)
                }
                Text(
                    text = "${accommodation.priceKrw} won",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.outline
                    ),
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun TextItem(text: String, imageVector: ImageVector) {
    Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = {}, modifier = Modifier.size(24.dp)) {
            Icon(imageVector = imageVector, contentDescription = null, Modifier.size(12.dp))

        }
        Spacer(Modifier.width(4.dp))
        Text(text = text, style = MaterialTheme.typography.titleSmall)
    }
}