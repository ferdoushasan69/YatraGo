package com.dmmeta.choloja.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.GifBox
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import choloja.composeapp.generated.resources.Res
import coil3.compose.AsyncImage
import com.dmmeta.choloja.presentation.navigation.Screen
import com.dmmeta.choloja.presentation.screen.accommodation_details.accommodationList
import com.dmmeta.choloja.presentation.screen.component.CustomBannerSection
import com.dmmeta.choloja.presentation.screen.component.CustomOfferButton
import com.dmmeta.choloja.presentation.screen.component.CustomSearchBar
import com.dmmeta.choloja.presentation.screen.component.CustomTopAppBar
import com.dmmeta.choloja.presentation.screen.component.ProductItem
import com.dmmeta.choloja.presentation.theme.TopContentBrush
import com.dmmeta.choloja.utils.wideBreakPoint
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun HomeScreen(navController: NavHostController) {
    HomeContent(
        onCategoryItemClick = {
            navController.navigate(Screen.Category(it))
        },
        onBannerAddClick = {
            navController.navigate(Screen.ViewAllBanner)
        },
        onProductClick = {
            navController.navigate(Screen.AccommodationDetails(it))
        },
        onClick = {
            navController.navigate(Screen.Search(""))
        }
    )
}

@Composable
fun HomeContent(
    onCategoryItemClick: (String) -> Unit,
    onBannerAddClick: () -> Unit,
    onProductClick: (String) -> Unit,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
            .padding(bottom = 48.dp)
    ) {
        TopContent(onClick = onClick)
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp)
        ) {
            CategorySection(onCategoryItemClick = onCategoryItemClick)
            CustomBannerSection(onClick = onBannerAddClick)
            OfferButtonSection()
            ProductItemSection(onClick = onProductClick)
        }
    }
}

@Composable
fun ProductItemSection(onClick: (String) -> Unit) {

    val products = accommodationList()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "이런 상품은 어떠세요? 최근 본 상품과 비슷한 상품",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Spacer(Modifier.height(8.dp))
        LazyRow(modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(0.dp)) {
            items(products) { product ->
                ProductItem(
                    modifier = Modifier.padding(8.dp),
                    accommodation = product,
                    onClick = {
                        val json = Json.encodeToString(product)
                        onClick(json)
                    }
                )
            }
        }
    }
}

data class Product(val name: String, val img: Painter)

@Composable
fun OfferButtonSection() {
    val list = listOf(
        OfferButton(icon = Icons.Default.Percent, name = "최대3만쿠폰"),
        OfferButton(icon = Icons.Filled.GifBox, name = "NOL드로우"),
        OfferButton(icon = Icons.Default.CalendarMonth, name = "이번달쿠폰팩"),
        OfferButton(icon = Icons.Default.EventAvailable, name = "이벤트더보기"),
    )

    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val itemPerRow = if (maxWidth >= wideBreakPoint) 4 else 2

        Column(modifier = Modifier.fillMaxWidth()) {

            list.chunked(itemPerRow).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    rowItems.forEach { item ->
                        CustomOfferButton(
                            modifier = Modifier
                                .weight(1f),
                            imageVector = item.icon,
                            offerText = item.name
                        )
                    }

                    repeat(itemPerRow - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            AsyncImage(
                model = "https://image6.yanolja.com/cx-ydm/4ZWB2nf5o9R5M3sN",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().height(100.dp).padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}


data class OfferButton(
    val icon: ImageVector,
    val name: String
)


@Composable
fun CategorySection(onCategoryItemClick: (String) -> Unit) {

    val itemList = listOf(
        Item(
            itemName = "항공",
            icon = "https://image6.yanolja.com/cx-ydm/hYhG3cbSXLAcEysI"
        ),
        Item(
            itemName = "해외숙소",
            icon = "https://image6.yanolja.com/cx-ydm/RNr1sE2q8UA2UeUt"
        ),
        Item(
            itemName = "투어·티켓",
            icon = "https://image6.yanolja.com/cx-ydm/8iDpw62B6rzvMdEj"
        ),
        Item(
            itemName = "국내레저",
            icon = "https://image6.yanolja.com/cx-ydm/qf5b0xpM6bkaBUc8"
        ),
        Item(
            itemName = "공연",
            icon = "https://image6.yanolja.com/cx-ydm/FrHqBWRAWuqyRQtJ"
        ),
        Item(
            itemName = "호텔",
            icon = "https://image6.yanolja.com/cx-ydm/rlxTeITMziWMEtre"
        ),
        Item(
            itemName = "펜션",
            icon = "https://image6.yanolja.com/cx-ydm/rXvE3UHEQjKjfwY0"
        ),
        Item(
            itemName = "글램핑",
            icon = "https://image6.yanolja.com/cx-ydm/e21Gc4Jxgdelid8d"
        ),
        Item(
            itemName = "모텔",
            icon = "https://image6.yanolja.com/cx-ydm/GyXHCDFQVOib8jB6"
        ),
        Item(
            itemName = "교통",
            icon = "https://image6.yanolja.com/cx-ydm/aT8RWDTOWuyTqMDA"
        ),
        Item(
            itemName = "기차",
            icon = "https://yaimg.yanolja.com/joy/sunny/static/images/ic-nearme.png"
        ),
        Item(
            itemName = "제주항공",
            icon = "https://image6.yanolja.com/cx-ydm/W2QHf7vrkSpkpvQP"
        ),
        Item(
            itemName = "오션월드",
            icon = "https://image6.yanolja.com/cx-ydm/Gmir25Dz8iMLeOCI"
        ),
        Item(
            itemName = "고속버스",
            icon = "https://image6.yanolja.com/cx-ydm/ku2kYXw7MjhhAhyu"
        ),
        Item(
            itemName = "고속버스",
            icon = "https://image6.yanolja.com/cx-ydm/bE85nV1BiL8uH4PN"
        ),
    )


    val column = 5
    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        itemList.chunked(column).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                row.forEach { item ->
                    Box(modifier = Modifier.weight(1f)) {
                        CategoryItem(
                            imgUrl = item.icon,
                            categoryName = item.itemName,
                            onClick = onCategoryItemClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItem(imgUrl: String, categoryName: String, onClick: (String) -> Unit) {

    Column(
        modifier = Modifier.fillMaxWidth().clickable {
            onClick(categoryName)
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = imgUrl,
            contentDescription = null,
            modifier = Modifier.size(44.dp),
            contentScale = ContentScale.FillBounds
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = categoryName, style = MaterialTheme.typography.titleMedium,
            overflow = TextOverflow.Ellipsis
        )
    }
}


data class Item(
    val itemName: String,
    val icon: String
)

@Composable
private fun TopContent(onClick: () -> Unit) {
    var textValue by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = TopContentBrush,
                shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
            )

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth().padding(8.dp)
        ) {
            CustomTopAppBar(
                textStyle = MaterialTheme.typography.bodyMedium
                    .copy(fontWeight = FontWeight.Bold, fontSize = 33.sp),
                isHome = true,
                title = "Choloja",
                titleColor = Color.White,
                modifier = Modifier.fillMaxWidth().statusBarsPadding(),
                navIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = null,
                            tint = Color.White,
                        )
                    }
                },
                topBarAction = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = null,
                            tint = Color.White,
                        )
                    }
                }
            )

            CustomSearchBar(
                onValueChange = {
                    textValue = it
                },
                value = textValue,
                onClick = onClick,
                isSearch = true
            )
        }
    }
}

@Composable
private fun CustomDrawer(drawerState: DrawerState, modifier: Modifier = Modifier) {
    ModalDrawerSheet(
        drawerState = drawerState,
        modifier = modifier,
        drawerContentColor = MaterialTheme.colorScheme.surface,
        content = {
            Text("Okay")
        }
    )
}


@Preview
@Composable
fun HomeScreenPreView() {
//    HomeScreen()
//    TopContent()
}
