package com.dmmeta.nolapp.presentation.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.dmmeta.nolapp.presentation.screen.component.CustomOfferButton
import com.dmmeta.nolapp.presentation.screen.component.CustomSearchBar
import com.dmmeta.nolapp.presentation.screen.component.CustomTopAppBar
import com.dmmeta.nolapp.presentation.screen.component.PlayerBox
import com.dmmeta.nolapp.presentation.screen.theme.TopContentBrush
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nolapp.composeapp.generated.resources.Res
import nolapp.composeapp.generated.resources.banner_eight
import nolapp.composeapp.generated.resources.banner_eleven
import nolapp.composeapp.generated.resources.banner_fifteen
import nolapp.composeapp.generated.resources.banner_five
import nolapp.composeapp.generated.resources.banner_four
import nolapp.composeapp.generated.resources.banner_fourteen
import nolapp.composeapp.generated.resources.banner_nine
import nolapp.composeapp.generated.resources.banner_one
import nolapp.composeapp.generated.resources.banner_seven
import nolapp.composeapp.generated.resources.banner_six
import nolapp.composeapp.generated.resources.banner_sixteen
import nolapp.composeapp.generated.resources.banner_ten
import nolapp.composeapp.generated.resources.banner_thirteen
import nolapp.composeapp.generated.resources.banner_three
import nolapp.composeapp.generated.resources.banner_tweleve
import nolapp.composeapp.generated.resources.banner_two
import nolapp.composeapp.generated.resources.bus_transport
import nolapp.composeapp.generated.resources.calander
import nolapp.composeapp.generated.resources.camping
import nolapp.composeapp.generated.resources.cart
import nolapp.composeapp.generated.resources.charaki
import nolapp.composeapp.generated.resources.concert
import nolapp.composeapp.generated.resources.coupon
import nolapp.composeapp.generated.resources.event
import nolapp.composeapp.generated.resources.gift
import nolapp.composeapp.generated.resources.hotel
import nolapp.composeapp.generated.resources.jeju_air
import nolapp.composeapp.generated.resources.menu
import nolapp.composeapp.generated.resources.mutel
import nolapp.composeapp.generated.resources.ocean_old
import nolapp.composeapp.generated.resources.pention
import nolapp.composeapp.generated.resources.plan
import nolapp.composeapp.generated.resources.speed_bus
import nolapp.composeapp.generated.resources.tower
import nolapp.composeapp.generated.resources.train
import nolapp.composeapp.generated.resources.travel
import nolapp.composeapp.generated.resources.welcome_kit
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val wideBreakPoint = 600.dp

@Composable
fun HomeScreen() {
    HomeContent()
}

@Composable
fun HomeContent() {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        TopContent()
        Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            CategorySection()
            BannerSection()
            OfferButtonSection()
        }
    }
}

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
            // break into rows
            list.chunked(itemPerRow).forEach { rowItems ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    rowItems.forEach { item ->
                        CustomOfferButton(
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp),
                            painter = item.icon,
                            offerText = item.name
                        )
                    }

                    // If row is not full, add empty space to balance
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
                modifier = Modifier.fillMaxWidth()
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
fun BannerSection() {
    val pagerItems = listOf(
        painterResource(Res.drawable.banner_one),
        painterResource(Res.drawable.banner_two),
        painterResource(Res.drawable.banner_three),
        painterResource(Res.drawable.banner_four),
        painterResource(Res.drawable.banner_five),
        painterResource(Res.drawable.banner_six),
        painterResource(Res.drawable.banner_seven),
        painterResource(Res.drawable.banner_eight),
        painterResource(Res.drawable.banner_nine),
        painterResource(Res.drawable.banner_ten),
        painterResource(Res.drawable.banner_eleven),
        painterResource(Res.drawable.banner_tweleve),
        painterResource(Res.drawable.banner_thirteen),
        painterResource(Res.drawable.banner_fourteen),
        painterResource(Res.drawable.banner_fifteen),
        painterResource(Res.drawable.banner_sixteen)
    )



    if (pagerItems.isEmpty()) return

    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {

        val itemPerPage = if (maxWidth >= wideBreakPoint) 2 else 1
        val pageItem = remember(pagerItems, itemPerPage) {
            pagerItems.chunked(itemPerPage)
        }
        val realCount = pageItem.size
        if (realCount == 0) return@BoxWithConstraints


        val loopCount = realCount + 2;
        val pagerState = rememberPagerState(initialPage = 1, pageCount = {
            loopCount
        })


        var isPlaying by remember { mutableStateOf(true) }
        val scope = rememberCoroutineScope()
        LaunchedEffect(isPlaying, pagerState.currentPage, realCount) {
            if (!isPlaying) return@LaunchedEffect
            delay(5000)
            if (!pagerState.isScrollInProgress && pagerItems.isNotEmpty()) {
                pagerState.requestScrollToPage(pagerState.currentPage + 1)
            }
        }
        LaunchedEffect(pagerState.currentPage) {
            val p = pagerState.currentPage
            when (p) {
                0 -> {
                    pagerState.requestScrollToPage(realCount)
                }

                realCount + 1 -> {
                    pagerState.requestScrollToPage(1)
                }
            }
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            HorizontalPager(
                state = pagerState,
                snapPosition = SnapPosition.Start,
                modifier = Modifier.fillMaxWidth().height(150.dp),
                contentPadding = PaddingValues(0.dp),
                pageSize = PageSize.Fill,
            ) { page ->

                val realIndex = when (page) {
                    0 -> realCount - 1
                    realCount + 1 -> 0
                    else -> page - 1
                }
                val painters = pageItem[realIndex]

                if (painters.size == 1) {
                    Image(
                        painter = painters[0],
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    )
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painters[0],
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.weight(1f).fillMaxHeight()
                        )
                        if (painters.size > 1) {
                            Image(
                                painter = painters[1],
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier.weight(1f).fillMaxHeight()
                            )
                        } else {
                            Spacer(Modifier.weight(1f))
                        }
                    }
                }

            }

            val effectiveIndex = ((pagerState.currentPage - 1) % realCount + realCount) % realCount
            val displayPage = effectiveIndex + 1;
            val pageSize = realCount

            PlayerBox(
                current = pagerState.currentPage - 1,
                count = realCount,
                playing = isPlaying,
                onTogglePlay = {
                    isPlaying = !isPlaying
                },
                onSeek = { index ->
                    val target = (index + 1).coerceIn(1, realCount)
                    scope.launch {
                        pagerState.animateScrollToPage(target)
                    }
                },
                onNext = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                onPrev = {
                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                },
                currentPage = displayPage,
                pageSize = pageSize
            )
        }
    }

}

@Composable
fun CategorySection() {

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
            itemName = "해외 투어·티켓",
            icon = painterResource(Res.drawable.tower)
        ),
        Item(
            itemName = "국내레저",
            icon = painterResource(Res.drawable.charaki)
        ),
        Item(
            itemName = "공연/전시",
            icon = painterResource(Res.drawable.concert)
        ),
        Item(
            itemName = "호텔/리조트",
            icon = painterResource(Res.drawable.hotel)
        ),
        Item(
            itemName = "펜션/풀빌라",
            icon = painterResource(Res.drawable.pention)
        ),
        Item(
            itemName = "글램핑/카라반/캠핑",
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
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        itemList.chunked(column).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { item ->
                    Box(modifier = Modifier.weight(1f)) {
                        CategoryItem(
                            painter = item.icon,
                            categoryName = item.itemName
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItem(painter: Painter, categoryName: String) {

    Column(
        modifier = Modifier.fillMaxWidth(),
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
private fun TopContent() {
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
                modifier = Modifier.fillMaxWidth().statusBarsPadding(),
                navIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            painterResource(Res.drawable.menu),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
                topBarAction = {
                    Icon(
                        painterResource(Res.drawable.cart),
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
                value = textValue
            )
        }
    }
}


@Preview
@Composable
fun HomeScreenPreView() {
//    HomeScreen()
    TopContent()
}
