package com.dmmeta.choloja.presentation.screen.search_result

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dmmeta.choloja.presentation.navigation.Screen
import com.dmmeta.choloja.presentation.screen.accommodation_details.accommodationList
import com.dmmeta.choloja.presentation.screen.component.AccommodationListCard
import com.dmmeta.choloja.presentation.screen.component.CustomOutlineButton
import com.dmmeta.choloja.presentation.screen.component.PrimaryButton
import com.dmmeta.choloja.presentation.screen.component.RangeSeekBar
import kotlinx.serialization.json.Json


@Composable
fun SearchResultScreen(navHostController: NavHostController, query: String) {
    SearchContent(
        onBack = {
            navHostController.navigateUp()
        },
        query = query,
        goHome = {
            navHostController.navigate(Screen.Home)
        },
        onClick = {
            navHostController.navigate(Screen.AccommodationDetails(it))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchContent(
    onBack: () -> Unit, query: String,
    goHome: () -> Unit,
    onClick: (String) -> Unit
) {
    var textValue by remember { mutableStateOf(query) }
    var selected by remember { mutableStateOf(0) }
    val categories = listOf(
        "국내숙소",
        "국내레저",
        "공연/전시",
        "해외숙소",
        "해외투어티켓",
        "항공"
    )
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showFilterBottomSheet by remember { mutableStateOf(false) }
    var showSortBottomSheet by remember { mutableStateOf(false) }

    if (showSortBottomSheet) {
        SortBottomSheet(
            onDismiss = {
                showSortBottomSheet = false
            },
            sheetState = sheetState,
            onClick = { itemName ->

            }
        )
    }
    if (showFilterBottomSheet) {
        FilterBottomSheet(
            onDismissRequest = {
                showFilterBottomSheet = false
            },
            modifier = Modifier.fillMaxWidth(),
            sheetState = sheetState,
            onClick = { itemName ->

            }
        )
    }
    Column(modifier = Modifier.fillMaxSize()) {
        CustomSearchBar(
            onValueChange = {
                textValue = it
            },
            value = textValue,
            onClear = { textValue = "" },
            onBack = onBack,
            goHome = goHome
        )
        LazyRow(modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(8.dp)) {
            itemsIndexed(categories) { index, category ->

                CategoryItem(
                    modifier = Modifier.padding(8.dp),
                    category = category,
                    onClick = {
                        selected = index
                    },
                    selected = selected == index
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 18.dp),
            color = MaterialTheme.colorScheme.outlineVariant.copy(.3f)
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomOutlineButton(
                modifier = Modifier.weight(1f),
                buttonText = "09.17~09.18 · 객실 1, 2명",
                onClick = {},
                textStyle = MaterialTheme.typography.titleSmall,
                iconTint = MaterialTheme.colorScheme.onBackground,
                imageVector = Icons.Default.EditCalendar,
            )
            Spacer(Modifier.width(8.dp))
            CustomOutlineButton(
                modifier = Modifier,
                imageVector = Icons.Default.FilterList,
                buttonText = "필터",
                onClick = {
                    showFilterBottomSheet = true
                },
                textStyle = MaterialTheme.typography.titleSmall,
                iconTint = MaterialTheme.colorScheme.onBackground

            )
            Spacer(Modifier.width(8.dp))

            CustomOutlineButton(
                modifier = Modifier,
                buttonText = "정렬",
                onClick = {
                    showSortBottomSheet = true
                },
                textStyle = MaterialTheme.typography.titleSmall,
                iconTint = MaterialTheme.colorScheme.onBackground,
                imageVector = Icons.Default.SwapVert
            )
        }

        Row(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 8.dp)) {
            CustomFutureBadgeButton(
                text = "조식포함",
                onClick = {}
            )
            CustomFutureBadgeButton(text = "무료취소", onClick = {})
            CustomFutureBadgeButton(text = "예약가능", onClick = {})
        }
        val items = accommodationList()
        val count = items.size
        LazyColumn {
            items(items) {
                val hotel = Json.encodeToString(it)
                AccommodationListCard(
                    onClick = {
                        onClick(hotel)
                    },
                    accommodation = it
                )

                if (count > 1 || count == items.size - 1) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 18.dp),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(.3f)
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortBottomSheet(
    onDismiss: () -> Unit,
    sheetState: SheetState,
    onClick: (String) -> Unit
) {
    val list = listOf(
        "추천순",
        "가격 낮은 순",
        "가격 높은 순"
    )
    var selected by remember { mutableStateOf(-1) }
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
        Column(
            Modifier.fillMaxWidth().padding(horizontal = 8.dp).navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                "정렬",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            list.forEachIndexed { index, string ->
                SortSheetItem(
                    text = string,
                    onClick = {
                        selected = if (it) index else -1
                        onClick(string)
                    },
                    selected = index == selected
                )
            }

        }
    }
}

@Composable
fun SortSheetItem(text: String, onClick: (Boolean) -> Unit, selected: Boolean) {
    Row(
        modifier = Modifier.padding(8.dp).fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (selected) MaterialTheme.colorScheme.outlineVariant.copy(.5f)
                else Color.Transparent, shape = RoundedCornerShape(8.dp)
            )

            .clickable {
                onClick(!selected)
            }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = text, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.width(3.dp))
            if (selected) {
                Icon(
                    imageVector = Icons.Default.Info,
                    modifier = Modifier.size(15.dp),
                    contentDescription = null
                )
            }
        }
        if (selected) {
            Icon(
                imageVector = Icons.Default.Check,
                modifier = Modifier.size(24.dp),
                contentDescription = null
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    onClick: (String) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        content = {
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                Text(
                    text = "필터",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Text("자주 찾는 필터", style = MaterialTheme.typography.titleMedium)

                FilterItem(
                    onClick = onClick
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = MaterialTheme.colorScheme.outlineVariant.copy(.3f)
                )

                val rangeState = remember { mutableStateOf(20f..80f) }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("가격 범위", style = MaterialTheme.typography.titleSmall)
                    Text("정액 2.0명 0.1억 기준", style = MaterialTheme.typography.titleSmall)
                }
                RangeSeekBar(
                    value = rangeState.value,
                    onValueChange = { rangeState.value = it },
                    valueRange = 0f..100f,
                    steps = 1,
                    showValueLabels = true,
                    valueFormatter = { v -> "${'$'}{v.toInt()}" }
                )

                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                    CustomOutlineButtonMinMax(
                        minOrMax = "최소",
                        onClick = {},
                        modifier = Modifier.weight(1f),
                        value = "${rangeState.value}"
                    )
                    Spacer(Modifier.width(8.dp))
                    CustomOutlineButtonMinMax(
                        minOrMax = "최대",
                        onClick = {},
                        modifier = Modifier.weight(1f),
                        value = "${rangeState.value}"
                    )
                }
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {

                    PrimaryButton(
                        modifier = Modifier,
                        text = "초기화",
                        onClick = {},
                        containerColor = MaterialTheme.colorScheme.outlineVariant.copy(.2f),
                        contentColor = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(Modifier.width(8.dp))
                    PrimaryButton(
                        modifier = Modifier.weight(1f),
                        text = "적용하기",
                        onClick = {}
                    )
                }

            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterItem(modifier: Modifier = Modifier, onClick: (String) -> Unit) {
    val list = listOf(
        "예약 가능한 숙소",
        "조식 포함",
        "무료 와이파이",
        "무료 취소 가능 숙소",
        "NO.1 특가 가능 숙소"
    )
    var selectedItem by remember { mutableStateOf(setOf<Int>()) }
    list.forEachIndexed { index, string ->
        Row(
            modifier = modifier.fillMaxWidth().padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(string)
            CustomCheckbox(
                checked = index in selectedItem,
                onCheckedChange = { isChecked ->
                    selectedItem = if (isChecked) selectedItem + index else selectedItem - index
                    onClick(string)
                },
                modifier = Modifier,
            )
        }
    }


}

@Composable
fun CustomOutlineButtonMinMax(
    minOrMax: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    value: String
) {

    Column(
        modifier = modifier.border(
            width = 1.dp, shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.outlineVariant.copy(.3f)
        )
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onClick()
            }
            .padding(horizontal = 8.dp, vertical = 12.dp)
    ) {
        Text(text = minOrMax, style = MaterialTheme.typography.labelSmall)
        Text(
            text = value,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
        )
    }
}


@Composable
fun CustomCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val checkboxColor by animateColorAsState(
        targetValue = if (checked) Color(0xFF3B82F6) else Color.White,
        label = "checkboxColor"
    )

    val borderColor by animateColorAsState(
        targetValue = if (checked) MaterialTheme.colorScheme.outlineVariant.copy(.1f) else MaterialTheme.colorScheme.outlineVariant.copy(
            .7f
        ),
        label = "borderColor"
    )

    val scale by animateFloatAsState(
        targetValue = if (checked) 1f else 0f,
        label = "checkScale"
    )

    Row(
        modifier = modifier
            .padding(8.dp)
            .clickable { onCheckedChange(!checked) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .background(
                    color = checkboxColor,
                    shape = RoundedCornerShape(4.dp)
                )
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(4.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(16.dp)
                    .scale(scale)
            )
        }

    }
}

@Composable
private fun CategoryItem(
    modifier: Modifier = Modifier,
    category: String,
    onClick: () -> Unit,
    selected: Boolean
) {
    Box(
        modifier = modifier.background(
            if (selected) MaterialTheme.colorScheme.outline else Color.Transparent,
            shape = RoundedCornerShape(20.dp)
        )
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                onClick()
            }
            .padding(8.dp)
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.titleMedium,
            color = if (selected) Color.White else MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun CustomFutureBadgeButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick, colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.outlineVariant.copy(.2f)
        ), modifier = modifier.padding(end = 8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomSearchBar(
    onValueChange: (String) -> Unit = {},
    value: String = "",
    onClear: () -> Unit,
    onBack: () -> Unit,
    goHome: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = null,
                Modifier.size(24.dp)
            )
        }

        TextField(
            modifier = Modifier.weight(1f).height(56.dp).clip(RoundedCornerShape(16.dp)),
            onValueChange = onValueChange,
            suffix = {
                if (value.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp).clip(CircleShape).clickable { onClear() }
                    )
                }
            },
            placeholder = { Text("Search here...") },
            singleLine = true,
            value = value, colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.outlineVariant.copy(.2f),
                unfocusedContainerColor = MaterialTheme.colorScheme.outlineVariant.copy(.2f),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,

                )
        )
        IconButton(onClick = goHome) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

