package com.dmmeta.nolapp.presentation.screen.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dmmeta.nolapp.presentation.navigation.Screen
import com.dmmeta.nolapp.presentation.theme.PrimaryColor
import nolapp.composeapp.generated.resources.Res
import nolapp.composeapp.generated.resources.ic_favorite
import nolapp.composeapp.generated.resources.ic_home
import nolapp.composeapp.generated.resources.ic_near_by
import nolapp.composeapp.generated.resources.ic_user
import nolapp.composeapp.generated.resources.search
import org.jetbrains.compose.resources.painterResource

data class BottomNavItem(
    val label: String,
    val selectedIcon: Painter,
    val unSelectedIcon: Painter,
    val route: String
)

@Composable
fun AppBottomNavigationBar(
    navController: NavHostController,
    containerColor: Color = MaterialTheme.colorScheme.background,
    selectedContentColor: Color = PrimaryColor,
    unSelectedContentColor: Color = MaterialTheme.colorScheme.onSurface,
) {


    val items = listOf(
        BottomNavItem(
            label = "검색",
            selectedIcon = painterResource(Res.drawable.search),
            unSelectedIcon = painterResource(Res.drawable.search),
            route = Screen.Search::class.qualifiedName!!
        ),
        BottomNavItem(
            label = "내주변",
            selectedIcon = painterResource(Res.drawable.ic_near_by),
            unSelectedIcon = painterResource(Res.drawable.ic_near_by),
            route = Screen.Map::class.qualifiedName!!
        ),
        BottomNavItem(
            label = "홈",
            selectedIcon = painterResource(Res.drawable.ic_home),
            unSelectedIcon = painterResource(Res.drawable.ic_home),
            route = Screen.Home::class.qualifiedName!!
        ),
        BottomNavItem(
            label = "찜",
            selectedIcon = painterResource(Res.drawable.ic_favorite),
            unSelectedIcon = painterResource(Res.drawable.ic_favorite),
            route = Screen.Favorite::class.qualifiedName!!
        ),
        BottomNavItem(
            label = "마이",
            selectedIcon = painterResource(Res.drawable.ic_user),
            unSelectedIcon = painterResource(Res.drawable.ic_user),
            route = Screen.Profile::class.qualifiedName!!
        ),
    )

    val currentRoute =
        navController.currentBackStackEntryAsState().value?.destination?.route?.substringBefore("?")
    val bottomNavBarDestination = Screen.Home::class.qualifiedName!!

    Row(
        modifier = Modifier.fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items.forEachIndexed { index, item ->
            BottomNavItemView(
                modifier = Modifier.weight(1f),
                item = item,
                isSelected = currentRoute == item.route,
                unSelectedContentColor = unSelectedContentColor,
                selectedContentColor = selectedContentColor,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(bottomNavBarDestination) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
            )
        }
    }
}


@Composable
fun BottomNavItemView(
    modifier: Modifier = Modifier,
    item: BottomNavItem,
    isSelected: Boolean,
    unSelectedContentColor: Color,
    selectedContentColor: Color,
    onClick: () -> Unit,
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.10f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
    )

    val contentColor by animateColorAsState(
        targetValue = if (isSelected) selectedContentColor else unSelectedContentColor
    )

    Column(
        modifier = modifier
            .navigationBarsPadding()
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Image(
            painter = if (isSelected) item.selectedIcon else item.unSelectedIcon,
            contentDescription = item.label,
            colorFilter = if (!isSelected) ColorFilter.tint(unSelectedContentColor) else ColorFilter.tint(
                PrimaryColor
            ),
            modifier = Modifier

        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = item.label,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelLarge,
            color = contentColor
        )
    }
}