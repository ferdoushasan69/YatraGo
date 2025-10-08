package com.dmmeta.yatrago.presentation.screen.component

import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.dmmeta.yatrago.utils.getBannerList
import com.dmmeta.yatrago.utils.wideBreakPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CustomBannerSection(onClick: () -> Unit = {}) {
    val pagerItems = getBannerList()


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
        Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            HorizontalPager(
                state = pagerState,
                snapPosition = SnapPosition.Start,
                modifier = Modifier.fillMaxWidth().height(150.dp),
                pageSize = PageSize.Fill,
            ) { page ->

                val realIndex = when (page) {
                    0 -> realCount - 1
                    realCount + 1 -> 0
                    else -> page - 1

                }
                val painters = pageItem[realIndex]

                if (painters.size == 1) {
                    AsyncImage(
                        model = painters[0],
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp))
                            .padding(horizontal = 8.dp)
                    )

                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = painters[0],
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.weight(1f).fillMaxHeight()
                                .clip(RoundedCornerShape(16.dp))

                        )
                        Spacer(Modifier.width(8.dp))
                        if (painters.size > 1) {
                            AsyncImage(
                                model = painters[1],
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier.weight(1f).fillMaxHeight()
                                    .clip(RoundedCornerShape(16.dp))

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
                currentPage = displayPage,
                pageSize = pageSize,
                onAdd = onClick
            )
        }
    }

}