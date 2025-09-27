package com.dmmeta.yatrago.presentation.screen.view_all_banner

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dmmeta.yatrago.presentation.screen.component.CustomTopAppBar
import com.dmmeta.yatrago.utils.getBannerList
import com.dmmeta.yatrago.utils.wideBreakPoint
import yatrago.composeapp.generated.resources.Res
import yatrago.composeapp.generated.resources.ic_back
import org.jetbrains.compose.resources.painterResource

@Composable
fun BannerListScreen(navHostController: NavHostController) {
    BannerListContent(onBack = {
        navHostController.navigateUp()
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BannerListContent(onBack : ()-> Unit) {
    val bannerList = getBannerList()

    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CustomTopAppBar(
            title = "전체보기",
            textStyle = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onBackground),
            modifier = Modifier.fillMaxWidth(),
            navIcon = {
                IconButton(onClick = onBack) {
                    Icon(painter = painterResource(Res.drawable.ic_back), contentDescription = null)
                }
            }
        )
        BoxWithConstraints {
            val itemPerPage = if (maxWidth >= wideBreakPoint) 2 else 1

            LazyVerticalGrid(
                columns = GridCells.Fixed(itemPerPage),
            ) {
                items(bannerList) {
                    Image(
                        painter = it,
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxWidth()
                            .height(150.dp)
                            .padding(
                                vertical = 8.dp,
                                horizontal = if (maxWidth >= wideBreakPoint) 8.dp else 0.dp
                            )
                            .clip(RoundedCornerShape(16.dp))

                    )
                }
            }

        }
    }
}