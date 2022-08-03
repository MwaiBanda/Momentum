package com.mwaibanda.momentum.android.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mwaibanda.momentum.android.core.utils.ScreenConfiguration
import com.mwaibanda.momentum.android.core.utils.ScreenConfiguration.ScreensWithoutNavigation
import com.mwaibanda.momentum.android.presentation.components.rememberBottomSheetNavigator
import com.mwaibanda.momentum.android.presentation.navigation.BottomBar
import com.mwaibanda.momentum.android.presentation.navigation.TopBar

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalMaterialNavigationApi
@Composable
fun MomentumEntry(content: @Composable (PaddingValues, NavHostController, BottomSheetNavigator) -> Unit) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = if (ScreenConfiguration.ScreensWithWhiteStatusBar.screens.contains(currentRoute))  Color.White else Color.Transparent,
            darkIcons = useDarkIcons
        )

    }


    Scaffold() {
        Box {
            content(it, navController, bottomSheetNavigator)
            if (ScreensWithoutNavigation.screens.contains(currentRoute).not()) {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    TopBar(navController = navController, currentRoute = currentRoute)
                    BottomBar(navController = navController, currentRoute = currentRoute)

                }
            }
        }
    }
}
