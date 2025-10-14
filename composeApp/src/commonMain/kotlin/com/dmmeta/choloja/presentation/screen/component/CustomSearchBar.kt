package com.dmmeta.choloja.presentation.screen.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dmmeta.choloja.presentation.theme.SearchBarColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    onValueChange: (String) -> Unit,
    value: String,
    isSearch: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        placeholder = {
            Text(text = "무엇을 찾아드릴까요?", color = Color.White.copy(.7f))
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(
                8.dp
            )
            .clip(RoundedCornerShape(40.dp))
            .isSearch(
                isSearch,
                onClick = {
                    onClick()
                }
            ),
        onValueChange = onValueChange,
        prefix = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Color.White.copy(.7f),
                modifier = Modifier.size(26.dp)
            )
        },
        singleLine = true,
        value = value,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = SearchBarColor.copy(.2f),
            unfocusedContainerColor = SearchBarColor.copy(.2f),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
        )
    )
}

@Composable
fun Modifier.isSearch(isSearch: Boolean, onClick: () -> Unit): Modifier {
    return if (isSearch) {
        onFocusChanged {
            if (it.isFocused) {
                onClick()
            }
        }
    } else {
        Modifier
    }
}
