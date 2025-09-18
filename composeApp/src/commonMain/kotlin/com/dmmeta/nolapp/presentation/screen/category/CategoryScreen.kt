package com.dmmeta.nolapp.presentation.screen.category

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
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dmmeta.nolapp.presentation.navigation.Screen
import com.dmmeta.nolapp.presentation.screen.component.CustomBannerSection
import com.dmmeta.nolapp.presentation.screen.component.CustomSearchBox
import com.dmmeta.nolapp.presentation.screen.component.CustomTopAppBar
import nolapp.composeapp.generated.resources.Res
import nolapp.composeapp.generated.resources.cart
import nolapp.composeapp.generated.resources.ic_back
import nolapp.composeapp.generated.resources.search
import org.jetbrains.compose.resources.painterResource

@Composable
fun CategoryScreen(categoryName: String, navHostController: NavHostController) {
    CategoryContent(categoryName = categoryName, onBack = {
        navHostController.navigateUp()
    }, onSearch = {

    }, onBannerList = {
        navHostController.navigate(Screen.ViewAllBanner)
    })
}

@Composable
fun CategoryContent(
    categoryName: String,
    onBack: () -> Unit,
    onSearch: () -> Unit, onBannerList: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        CustomTopAppBar(
            title = categoryName,
            modifier = Modifier.fillMaxWidth(),
            navIcon = {
                IconButton(onClick = onBack) {
                    Icon(painter = painterResource(Res.drawable.ic_back), contentDescription = null)
                }
            },
            topBarAction = {
                Row(modifier = Modifier.padding(end = 8.dp)) {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(Res.drawable.search),
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = onSearch) {
                        Icon(
                            painter = painterResource(Res.drawable.cart),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
            textStyle = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.background)
        )

        CustomSearchBox(
            onSearch = {

            }
        )
        Spacer(Modifier.height(8.dp))
        CustomBannerSection {
            onBannerList()
        }
    }
}