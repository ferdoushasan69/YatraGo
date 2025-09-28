package com.dmmeta.yatrago.presentation.screen.category_search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dmmeta.yatrago.presentation.screen.component.CustomSearchBox
import com.dmmeta.yatrago.presentation.screen.component.CustomTopAppBar
import com.dmmeta.yatrago.presentation.screen.component.SearchListTile
import com.dmmeta.yatrago.presentation.theme.PrimaryColor
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import yatrago.composeapp.generated.resources.Res
import yatrago.composeapp.generated.resources.ic_apertment
import yatrago.composeapp.generated.resources.ic_back
import yatrago.composeapp.generated.resources.ic_flight
import yatrago.composeapp.generated.resources.ic_mic
import yatrago.composeapp.generated.resources.ic_park
import yatrago.composeapp.generated.resources.tower

@Composable
fun CategorySearchScreen(
    navHostController: NavHostController,
    viewModel: CategorySearchViewModel = koinViewModel()
) {


    CategorySearchContent(
        onBack = {
            navHostController.navigateUp()
        },
        viewModel = viewModel
    )
}

@Composable
private fun CategorySearchContent(
    onBack: () -> Unit,
    viewModel: CategorySearchViewModel
) {
    val focusRequester = remember { FocusRequester() }
    val locationList = listOf(
        LocationItem("Dhaka", "2025-09-24"),
        LocationItem("Chattogram", "2025-09-25"),
        LocationItem("Cox’s Bazar", "2025-09-26"),
        LocationItem("Sylhet", "2025-09-27"),
        LocationItem("Sundarbans", "2025-09-28"),
        LocationItem("Rangamati", "2025-09-29"),
        LocationItem("Bandarban", "2025-09-30"),
        LocationItem("Khulna", "2025-10-01"),
        LocationItem("Rajshahi", "2025-10-02"),
        LocationItem("Kuakata", "2025-10-03")
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    val categories = listOf(
        CategoryOption(label = "국내패키지", icon = painterResource(Res.drawable.ic_park)),
        CategoryOption(label = "공연", icon = painterResource(Res.drawable.ic_mic)),
        CategoryOption(label = "해외숙소", icon = painterResource(Res.drawable.ic_apertment)),
        CategoryOption(label = "해외투어", icon = painterResource(Res.drawable.tower)),
        CategoryOption(label = "항공", icon = painterResource(Res.drawable.ic_flight))
    )
    var selectedIndex by remember { mutableStateOf(2) }
    var categoryName by remember { mutableStateOf(categories[selectedIndex].label) }

    var textValue by remember { mutableStateOf("") }

    val filteredResults = viewModel.filterResult
    val isSearchEmpty = viewModel.isSearchEmpty

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        CustomTopAppBar(
            navIcon = {
                IconButton(onClick = onBack, modifier = Modifier) {
                    Icon(
                        painterResource(Res.drawable.ic_back),
                        contentDescription = null
                    )
                }
            },
        )


        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            itemsIndexed(categories) { index, item ->
                CategoryItem(
                    label = item.label,
                    icon = item.icon,
                    modifier = Modifier.padding(8.dp),
                    onClick = {
                        selectedIndex = index
                        categoryName = item.label
                    },
                    selected = selectedIndex == index
                )
            }
        }

        CustomSearchBox(
            isSearch = false,
            onSearch = {
            },
            modifier = Modifier.padding(16.dp).focusRequester(focusRequester),
            categoryName = categoryName,
            onValueChange = {
                textValue = it
                viewModel.onSearch(query = textValue, locationList)
            },
            value = textValue
        )

        if (textValue.isNotBlank()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Search Results",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                if (isSearchEmpty) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No items found for \"$textValue\"",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    filteredResults.forEach { item ->
                        SearchListTile(
                            locationName = item.location,
                            dateInfo = item.date,
                            onCancel = { },
                        )
                    }
                }
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "최근 검색", style = MaterialTheme.typography.titleMedium)
                Text(text = "$categoryName", style = MaterialTheme.typography.titleMedium)
            }

            viewModel.recentSearch.take(3).forEach { item ->
                SearchListTile(
                    locationName = item.location,
                    dateInfo = item.date,
                    onCancel = { viewModel.removeSearch(item) }
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "인기 검색어",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                "2025.09.17 기준", style = MaterialTheme.typography.titleSmall.copy(
                    color = MaterialTheme.colorScheme.onBackground.copy(.5f)
                )
            )
        }
    }
}

data class LocationItem(
    val location: String,
    val date: String,
)

data class CategoryOption(
    val icon: Painter,
    val label: String
)

@Composable
private fun CategoryItem(
    label: String,
    modifier: Modifier = Modifier,
    icon: Painter,
    onClick: () -> Unit,
    selected: Boolean
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        ElevatedCard(
            modifier = Modifier
                .size(55.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = if (selected) PrimaryColor else MaterialTheme.colorScheme.surfaceBright
            ),
            elevation = CardDefaults.elevatedCardElevation(10.dp),
            onClick = onClick

        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp),
                    tint = if (selected) Color.White else PrimaryColor
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        Text(text = label, style = MaterialTheme.typography.titleMedium)

    }
}

@Composable
private fun Modifier.showBgColor(isShow: Boolean): Modifier {
    return if (isShow) {
        background(PrimaryColor)
    } else {
        Modifier
    }
}