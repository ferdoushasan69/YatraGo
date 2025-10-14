package com.dmmeta.choloja.presentation.screen.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CartScreen(viewModel: CartViewModel = koinViewModel()) {
    val items = viewModel.items.collectAsState().value

    LazyColumn {
        items(items) {
            CartItem(
                img = it.photoUrl,
                name = it.name,
                address = it.address,
                disCount = it.discount,
                total = it.priceKrw
            )
        }
    }

}

@Composable
fun CartItem(img: String, name: String, address: String, disCount: String, total: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        AsyncImage(
            model = img,
            contentDescription = null,
            modifier = Modifier.width(60.dp).height(80.dp).clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.FillBounds
        )
        Spacer(Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(name)
            Text(address)
        }
        Column {
            Text(disCount)
            Text(text = "$total won")
        }
    }
}