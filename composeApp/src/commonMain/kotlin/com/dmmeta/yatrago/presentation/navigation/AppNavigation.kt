package com.dmmeta.yatrago.presentation.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.dmmeta.yatrago.presentation.screen.accommodation_details.AccommodationDetailsScreen
import com.dmmeta.yatrago.presentation.screen.cart.CartScreen
import com.dmmeta.yatrago.presentation.screen.category.CategoryScreen
import com.dmmeta.yatrago.presentation.screen.category_search.CategorySearchScreen
import com.dmmeta.yatrago.presentation.screen.component.AppBottomNavigationBar
import com.dmmeta.yatrago.presentation.screen.home.HomeScreen
import com.dmmeta.yatrago.presentation.screen.map.MapScreen
import com.dmmeta.yatrago.presentation.screen.search_result.SearchResultScreen
import com.dmmeta.yatrago.presentation.screen.view_all_banner.BannerListScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = remember(backStackEntry) {
        backStackEntry?.destination?.route?.substringBefore("?")?.substringBefore("/")
    }

    val topBarScreens = remember { setOf(Screen.Home::class.qualifiedName) }
    val fullScreens = remember {
        setOf(
            Screen.Home::class.qualifiedName,
            Screen.AccommodationDetails::class.qualifiedName
        )
    }
    val bottomScreens = remember {
        setOf(
            Screen.SearchFilter::class.qualifiedName,
            Screen.Map::class.qualifiedName,
            Screen.Home::class.qualifiedName,
            Screen.Favorite::class.qualifiedName,
            Screen.Profile::class.qualifiedName
        )
    }

    val isFullScreen = remember(currentRoute) { currentRoute in fullScreens }
    val showBottomBar = remember(currentRoute) { currentRoute in bottomScreens }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                AppBottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        val animatedTopPadding by animateDpAsState(
            targetValue = if (!isFullScreen) innerPadding.calculateTopPadding() else 0.dp,
            animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
            label = "top_padding"
        )
        val animatedBottomPadding by animateDpAsState(
            targetValue = if (!isFullScreen) innerPadding.calculateBottomPadding() else 0.dp,
            animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
            label = "bottom_padding"
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = animatedTopPadding, bottom = animatedBottomPadding)
        ) {
            NavHost(
                navController = navController,
                modifier = Modifier.fillMaxSize(),
                startDestination = Screen.Home,
                enterTransition = { fadeIn(animationSpec = tween(durationMillis = 300)) },
                exitTransition = { fadeOut(animationSpec = tween(durationMillis = 300)) }
            ) {
                composable<Screen.Home> { HomeScreen(navController) }

                composable<Screen.Category> {
                    val args = it.toRoute<Screen.Category>()
                    CategoryScreen(
                        categoryName = args.categoryName, navHostController = navController,
                    )
                }

                composable<Screen.CategorySelection> {
                    CategorySearchScreen(navHostController = navController)
                }

                composable<Screen.ViewAllBanner> {
                    BannerListScreen(navController)
                }

                composable<Screen.Search> {
                    val args = it.toRoute<Screen.Search>().query
                    SearchResultScreen(navController, query = args)
                }

                composable<Screen.SearchFilter> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("SearchFilter")
                    }
                }
                composable<Screen.Map> {
                    KeepAlive { MapScreen(navController) }
                }

                composable<Screen.AccommodationDetails> {
                    val args = it.toRoute<Screen.AccommodationDetails>().json
                    AccommodationDetailsScreen(json = args, navController)
                }

                composable<Screen.Favorite> {
                    CartScreen()
                }

                composable<Screen.Profile> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Profile")
                    }
                }
            }
        }
    }
}

@Composable
fun KeepAlive(content: @Composable () -> Unit) {
    val saveableStateHolder = rememberSaveableStateHolder()
    saveableStateHolder.SaveableStateProvider("map") {
        content()
    }
}
