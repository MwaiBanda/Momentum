package com.mwaibanda.momentum.android.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes
import com.mwaibanda.momentum.android.core.utils.ScreenConfiguration

@Composable
fun BottomBar(navController: NavController, currentRoute: String?) {
    val context = LocalContext.current
    val bottomTabs = listOf(
        NavigationRoutes.OfferScreen,
        NavigationRoutes.SermonScreen
    )

    Column {
        BottomNavigation(
            elevation = 0.dp,
            backgroundColor = if (ScreenConfiguration.ScreensWithTransparentBottomBar.screens.contains(currentRoute)) Color.Transparent else Color.White
        ) {
            bottomTabs.forEach { bottomTab ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = bottomTab.icon ?: Icons.Default.Person,
                            contentDescription = context.getString(
                                bottomTab.tabName ?: 0
                            )
                        )
                    },
                    selectedContentColor = Color(C.MOMENTUM_ORANGE),
                    alwaysShowLabel = false,
                    selected = currentRoute == bottomTab.route,
                    onClick = {
                        navController.navigate(bottomTab.route) {

                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
        Spacer(
            Modifier
                .background(if (ScreenConfiguration.ScreensWithTransparentBottomBar.screens.contains(currentRoute)) Color.Transparent else Color.White)
                .windowInsetsBottomHeight(WindowInsets.navigationBars)
                .fillMaxWidth()
        )
    }
}