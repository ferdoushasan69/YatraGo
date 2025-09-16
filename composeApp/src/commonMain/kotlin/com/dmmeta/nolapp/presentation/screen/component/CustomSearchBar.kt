package com.dmmeta.nolapp.presentation.screen.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dmmeta.nolapp.presentation.theme.SearchBarColor
import nolapp.composeapp.generated.resources.Res
import nolapp.composeapp.generated.resources.search
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(onValueChange: (String) -> Unit, value: String) {
    TextField(
        placeholder = {
            Text(text = "무엇을 찾아드릴까요?", color = Color.White.copy(.7f))
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                8.dp
            )
            .clip(RoundedCornerShape(40.dp)),
        onValueChange = onValueChange,
        prefix = {
            Icon(
                painterResource(Res.drawable.search),
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
