package com.dmmeta.nolapp.presentation.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.dmmeta.nolapp.presentation.screen.home.HomeScreen


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
    val isFullScreen = remember(currentRoute) { currentRoute in fullScreens }

    Scaffold { innerPadding ->
        val animatedPadding by animateDpAsState(
            targetValue = if (!isFullScreen) innerPadding.calculateTopPadding() else 0.dp,
            animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
            label = "top_padding"
        )

        val animatedBottomPadding by animateDpAsState(
            targetValue = if (!isFullScreen) innerPadding.calculateTopPadding() else 0.dp,
            animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
            label = "top_padding"
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
                startDestination = Screen.Home
            ) {
                composable<Screen.Home> {
                    HomeScreen(navController)
                }
                composable<Screen.Category> {
                    val name = it.toRoute<Screen.Category>()
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(name.categoryName)
                    }
                }
            }

        }

    }
}