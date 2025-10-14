package com.dmmeta.choloja.presentation.screen.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditLocationAlt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.EditLocationAlt
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dmmeta.choloja.presentation.navigation.Screen
import com.dmmeta.choloja.presentation.theme.PrimaryColor

data class BottomNavItem(
    val label: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
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
            selectedIcon = Icons.Filled.Search,
            unSelectedIcon = Icons.Outlined.Search,
            route = Screen.SearchFilter::class.qualifiedName!!
        ),
        BottomNavItem(
            label = "내주변",
            selectedIcon = Icons.Filled.EditLocationAlt,
            unSelectedIcon = Icons.Outlined.EditLocationAlt,
            route = Screen.Map::class.qualifiedName!!
        ),
        BottomNavItem(
            label = "홈",
            selectedIcon = Icons.Filled.Home,
            unSelectedIcon = Icons.Outlined.Home,
            route = Screen.Home::class.qualifiedName!!
        ),
        BottomNavItem(
            label = "찜",
            selectedIcon = Icons.Filled.Favorite,
            unSelectedIcon = Icons.Rounded.FavoriteBorder,
            route = Screen.Favorite::class.qualifiedName!!
        ),
        BottomNavItem(
            label = "마이",
            selectedIcon = Icons.Filled.Person,
            unSelectedIcon = Icons.Outlined.Person,
            route = Screen.Profile::class.qualifiedName!!
        ),
    )

    val currentRoute =
        navController.currentBackStackEntryAsState().value?.destination?.route?.substringBefore("?")
    val bottomNavBarDestination = Screen.Home::class.qualifiedName!!

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(containerColor),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items.forEach { item ->
            BottomNavItemView(
                modifier = Modifier.weight(1f),
                item = item,
                isSelected = currentRoute == item.route,
                selectedContentColor = selectedContentColor,
                unSelectedContentColor = unSelectedContentColor,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(bottomNavBarDestination) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun BottomNavItemView(
    modifier: Modifier = Modifier,
    item: BottomNavItem,
    isSelected: Boolean,
    selectedContentColor: Color,
    unSelectedContentColor: Color,
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

        Icon(
            imageVector = if (isSelected) item.selectedIcon else item.unSelectedIcon,
            contentDescription = item.label,
            tint = contentColor,
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
