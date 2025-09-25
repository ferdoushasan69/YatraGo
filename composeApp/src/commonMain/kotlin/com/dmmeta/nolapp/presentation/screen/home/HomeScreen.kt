package com.dmmeta.nolapp.presentation.screen.home

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dmmeta.nolapp.presentation.navigation.Screen
import com.dmmeta.nolapp.presentation.screen.component.CustomBannerSection
import com.dmmeta.nolapp.presentation.screen.component.CustomOfferButton
import com.dmmeta.nolapp.presentation.screen.component.CustomSearchBar
import com.dmmeta.nolapp.presentation.screen.component.CustomTopAppBar
import com.dmmeta.nolapp.presentation.screen.component.ProductItem
import com.dmmeta.nolapp.presentation.theme.TopContentBrush
import com.dmmeta.nolapp.utils.wideBreakPoint
import nolapp.composeapp.generated.resources.Res
import nolapp.composeapp.generated.resources.bus_transport
import nolapp.composeapp.generated.resources.calander
import nolapp.composeapp.generated.resources.camping
import nolapp.composeapp.generated.resources.charaki
import nolapp.composeapp.generated.resources.concert
import nolapp.composeapp.generated.resources.coupon
import nolapp.composeapp.generated.resources.event
import nolapp.composeapp.generated.resources.gift
import nolapp.composeapp.generated.resources.hotel
import nolapp.composeapp.generated.resources.ic_cart
import nolapp.composeapp.generated.resources.ic_menu
import nolapp.composeapp.generated.resources.jeju_air
import nolapp.composeapp.generated.resources.mutel
import nolapp.composeapp.generated.resources.ocean_old
import nolapp.composeapp.generated.resources.pention
import nolapp.composeapp.generated.resources.plan
import nolapp.composeapp.generated.resources.rahan_hotel
import nolapp.composeapp.generated.resources.speed_bus
import nolapp.composeapp.generated.resources.tower
import nolapp.composeapp.generated.resources.train
import nolapp.composeapp.generated.resources.travel
import nolapp.composeapp.generated.resources.welcome_kit
import org.jetbrains.compose.resources.painterResource
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
            navController.navigate(Screen.Map)
        },
        onClick = {
            navController.navigate(Screen.CategorySelection)
        }
    )
}

@Composable
fun HomeContent(
    onCategoryItemClick: (String) -> Unit,
    onBannerAddClick: () -> Unit,
    onProductClick: () -> Unit,
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
fun ProductItemSection(onClick: () -> Unit) {

    val products = listOf(
        Product(name = "포항 에이원호텔 해도포항 에이원호텔 해도", img = painterResource(Res.drawable.rahan_hotel)),
        Product(name = "포항 에이원호텔 해도포항 에이원호텔 해도", img = painterResource(Res.drawable.rahan_hotel)),
        Product(name = "포항 에이원호텔 해도포항 에이원호텔 해도", img = painterResource(Res.drawable.rahan_hotel)),
        Product(name = "포항 에이원호텔 해도포항 에이원호텔 해도", img = painterResource(Res.drawable.rahan_hotel)),
        Product(name = "포항 에이원호텔 해도포항 에이원호텔 해도", img = painterResource(Res.drawable.rahan_hotel)),
        Product(name = "포항 에이원호텔 해도포항 에이원호텔 해도", img = painterResource(Res.drawable.rahan_hotel)),
        Product(name = "포항 에이원호텔 해도포항 에이원호텔 해도", img = painterResource(Res.drawable.rahan_hotel)),
        Product(name = "포항 에이원호텔 해도포항 에이원호텔 해도", img = painterResource(Res.drawable.rahan_hotel)),
        Product(name = "포항 에이원호텔 해도포항 에이원호텔 해도", img = painterResource(Res.drawable.rahan_hotel)),
        Product(name = "포항 에이원호텔 해도포항 에이원호텔 해도", img = painterResource(Res.drawable.rahan_hotel)),
    )
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
                    productName = product.name,
                    productImage = product.img,
                    onClick = onClick
                )
            }
        }
    }
}

data class Product(val name: String, val img: Painter)

@Composable
fun OfferButtonSection() {
    val list = listOf(
        OfferButton(icon = painterResource(Res.drawable.coupon), name = "최대3만쿠폰"),
        OfferButton(icon = painterResource(Res.drawable.gift), name = "NOL드로우"),
        OfferButton(icon = painterResource(Res.drawable.calander), name = "이번달쿠폰팩"),
        OfferButton(icon = painterResource(Res.drawable.event), name = "이벤트더보기"),
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
                            painter = item.icon,
                            offerText = item.name
                        )
                    }

                    repeat(itemPerRow - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Image(
                painterResource(Res.drawable.welcome_kit),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}


data class OfferButton(
    val icon: Painter,
    val name: String
)


@Composable
fun CategorySection(onCategoryItemClick: (String) -> Unit) {

    val itemList = listOf(
        Item(
            itemName = "항공",
            icon = painterResource(Res.drawable.plan)
        ),
        Item(
            itemName = "해외숙소",
            icon = painterResource(Res.drawable.travel)
        ),
        Item(
            itemName = "투어·티켓",
            icon = painterResource(Res.drawable.tower)
        ),
        Item(
            itemName = "국내레저",
            icon = painterResource(Res.drawable.charaki)
        ),
        Item(
            itemName = "공연",
            icon = painterResource(Res.drawable.concert)
        ),
        Item(
            itemName = "호텔",
            icon = painterResource(Res.drawable.hotel)
        ),
        Item(
            itemName = "펜션",
            icon = painterResource(Res.drawable.pention)
        ),
        Item(
            itemName = "글램핑",
            icon = painterResource(Res.drawable.camping)
        ),
        Item(
            itemName = "모텔",
            icon = painterResource(Res.drawable.mutel)
        ),
        Item(
            itemName = "교통",
            icon = painterResource(Res.drawable.bus_transport)
        ),
        Item(
            itemName = "기차",
            icon = painterResource(Res.drawable.train)
        ),
        Item(
            itemName = "제주항공",
            icon = painterResource(Res.drawable.jeju_air)
        ),
        Item(
            itemName = "오션월드",
            icon = painterResource(Res.drawable.ocean_old)
        ),
        Item(
            itemName = "고속버스",
            icon = painterResource(Res.drawable.speed_bus)
        ),
        Item(
            itemName = "고속버스",
            icon = painterResource(Res.drawable.speed_bus)
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
                            painter = item.icon,
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
fun CategoryItem(painter: Painter, categoryName: String, onClick: (String) -> Unit) {

    Column(
        modifier = Modifier.fillMaxWidth().clickable {
            onClick(categoryName)
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painter,
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
    val icon: Painter
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
                title = "NOL",
                titleColor = Color.White,
                modifier = Modifier.fillMaxWidth().statusBarsPadding(),
                navIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            painterResource(Res.drawable.ic_menu),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
                topBarAction = {
                    Icon(
                        painterResource(Res.drawable.ic_cart),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
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
private fun CustomDrawer(drawerState: DrawerState,modifier: Modifier= Modifier){
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
