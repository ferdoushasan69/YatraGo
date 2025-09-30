package com.dmmeta.yatrago.presentation.screen.component

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import yatrago.composeapp.generated.resources.Res
import yatrago.composeapp.generated.resources.hotel_one
import yatrago.composeapp.generated.resources.ic_location


@Composable
fun AccommodationListCard(modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth().height(210.dp).padding(16.dp)) {
        Image(
            painterResource(Res.drawable.hotel_one),
            contentDescription = null,
            modifier = Modifier.width(120.dp).height(180.dp),
            contentScale = ContentScale.FillBounds
        )
        Spacer(Modifier.width(8.dp))
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxHeight()
        ) {
            Column(modifier = Modifier) {
                Text(
                    "호텔 ・ 4성급",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(.5f),
                    modifier = Modifier.padding(start = 4.dp)

                )
                Text(
                    "칸데오 호텔 오사카 난바",
                    style = MaterialTheme.typography.titleMedium
                         .copy(fontWeight = FontWeight.Bold)
                    ,
                    modifier = Modifier.padding(start = 4.dp)
                )

                TextItem(
                    text = "일본 ・ 오사카",
                    painter = painterResource(Res.drawable.ic_location),
                )
            }

            Column (Modifier.padding(horizontal = 16.dp)){
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Text("10%")
                    Spacer(Modifier.width(4.dp))
                    Text("70,000", textDecoration = TextDecoration.LineThrough)
                }
                Text(
                    "56,000원",
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
private fun TextItem(text: String, painter: Painter) {
    Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = {}, modifier = Modifier.size(24.dp)){
            Icon(painter = painter, contentDescription = null, Modifier.size(12.dp))

        }
        Spacer(Modifier.width(4.dp))
        Text(text = text, style = MaterialTheme.typography.titleSmall)
    }
}