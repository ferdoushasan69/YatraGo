package com.dmmeta.yatrago.presentation.screen.cart

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.dmmeta.yatrago.domain.model.Accommodation
import com.dmmeta.yatrago.presentation.screen.component.AccommodationListCard
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CartScreen(viewModel: CartViewModel= koinViewModel()){
    val items = viewModel.items.collectAsState().value

    LazyColumn {
        items(items){
           Text(it.name)
        }
    }

}
