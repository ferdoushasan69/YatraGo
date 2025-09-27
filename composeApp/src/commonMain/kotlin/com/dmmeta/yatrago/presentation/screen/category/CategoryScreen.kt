package com.dmmeta.yatrago.presentation.screen.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dmmeta.yatrago.presentation.navigation.Screen
import com.dmmeta.yatrago.presentation.screen.component.CustomBannerSection
import com.dmmeta.yatrago.presentation.screen.component.CustomSearchBox
import com.dmmeta.yatrago.presentation.screen.component.CustomTopAppBar
import yatrago.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.painterResource
import yatrago.composeapp.generated.resources.ic_back
import yatrago.composeapp.generated.resources.ic_cart
import yatrago.composeapp.generated.resources.search

@Composable
fun CategoryScreen(
    categoryName: String,
    navHostController: NavHostController,
) {
    CategoryContent(categoryName = categoryName, onBack = {
        navHostController.navigateUp()

    }, onSearch = {
        navHostController.navigate(Screen.CategorySelection) {
            launchSingleTop = true
            restoreState = true
        }
    }, onBannerList = {
        navHostController.navigate(Screen.ViewAllBanner) {
            launchSingleTop = true
            restoreState = true
        }
    }, onSearchBoxClick = {
        navHostController.navigate(Screen.CategorySelection) {
            launchSingleTop = true
            restoreState = true
        }
    })
}

@Composable
fun CategoryContent(
    categoryName: String,
    onBack: () -> Unit,
    onSearch: () -> Unit,
    onBannerList: () -> Unit,
    onSearchBoxClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CustomTopAppBar(
            title = categoryName,
            modifier = Modifier.fillMaxWidth(),
            navIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_back),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            topBarAction = {
                Row(modifier = Modifier.padding(end = 8.dp)) {
                    IconButton(onClick = onSearch) {
                        Icon(
                            painter = painterResource(Res.drawable.search),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_cart),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
            textStyle = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.background
            )
        )

        Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            CustomSearchBox(
                onSearch = onSearchBoxClick,
                modifier = Modifier,
                isSearch = true
            )
        }
        Spacer(Modifier.height(8.dp))
        CustomBannerSection {
            onBannerList()
        }
    }
}