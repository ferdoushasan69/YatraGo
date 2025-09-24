package com.dmmeta.nolapp.presentation.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import com.dmmeta.nolapp.presentation.screen.category.CategoryScreen
import com.dmmeta.nolapp.presentation.screen.category_search.CategorySearchScreen
import com.dmmeta.nolapp.presentation.screen.component.AppBottomNavigationBar
import com.dmmeta.nolapp.presentation.screen.home.HomeScreen
import com.dmmeta.nolapp.presentation.screen.map.MapScreen
import com.dmmeta.nolapp.presentation.screen.search.SearchScreen
import com.dmmeta.nolapp.presentation.screen.view_all_banner.BannerListScreen


@Composable
fun AppNavigation(navController: NavHostController) {

    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = remember(backStackEntry) {
        backStackEntry?.destination?.route?.substringBefore("?")?.substringBefore("/")
    }

    val topBarScreen = remember {
        setOf(Screen.Home::class.qualifiedName)
    }
    val fullScreens = remember {
        setOf(Screen.Home::class.qualifiedName)
    }

    val bottomScreen = remember {
        setOf(
            Screen.Search::class.qualifiedName,
            Screen.Map::class.qualifiedName,
            Screen.Home::class.qualifiedName,
            Screen.Favorite::class.qualifiedName,
            Screen.Profile::class.qualifiedName
        )
    }
    val isFullScreen = remember(currentRoute) { currentRoute in fullScreens }
    val showBottomBar = remember(currentRoute) { currentRoute in bottomScreen }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                AppBottomNavigationBar(
                    navController = navController
                )
            }

        }
    ) { innerPadding ->
        val animatedPadding by animateDpAsState(
            targetValue = if (!isFullScreen) innerPadding.calculateTopPadding() else 0.dp,
            animationSpec = tween(
                durationMillis = 250,
                easing = FastOutSlowInEasing
            ),
            label = "top_padding"
        )

        val animatedBottomPadding by animateDpAsState(
            targetValue = if (!isFullScreen) innerPadding.calculateBottomPadding() else 0.dp,
            animationSpec = tween(
                durationMillis = 250,
                easing = FastOutSlowInEasing
            ),
            label = "bottom_padding"
        )
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(
                    top = animatedPadding,
                    bottom = animatedBottomPadding
                )
        ) {
            NavHost(
                navController = navController,
                modifier = Modifier.fillMaxSize(),
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        )
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        )
                    ) + fadeOut(animationSpec = tween(300))
                },

                startDestination = Screen.Home,
            ) {
                composable<Screen.Home> {
                    HomeScreen(navController)
                }
                composable<Screen.Category> {
                    val args = it.toRoute<Screen.Category>()

                    CategoryScreen(
                        categoryName = args.categoryName,
                        navHostController = navController
                    )
                }

                composable<Screen.CategorySelection> {
                    CategorySearchScreen(navHostController = navController)
                }


                composable<Screen.ViewAllBanner> {
                    BannerListScreen(navController)
                }

                composable<Screen.Search> {
                    SearchScreen(navController)
                }

                composable<Screen.Map> {
                    KeepAlive { MapScreen(navController) }
                }

                composable<Screen.Favorite> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Favorite")
                    }
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
