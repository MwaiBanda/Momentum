package com.mwaibanda.momentum.android.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mwaibanda.momentum.android.core.utils.ScreenConfiguration
import com.mwaibanda.momentum.android.presentation.navigation.BottomBar
import com.mwaibanda.momentum.android.presentation.navigation.TopBar

@Composable
fun MomentumEntry(
    isShowingModal: Boolean,
    onShowModal: () -> Unit,
    content: @Composable (PaddingValues, NavHostController) -> Unit,
) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = if (ScreenConfiguration.ScreensWithWhiteStatusBar.screens.contains(currentRoute)) Color.White else Color.Transparent,
            darkIcons = useDarkIcons
        )

    }


    Scaffold {
        Box {
            Box(modifier = Modifier.zIndex(if (isShowingModal) 1f else 0f)) {
                content(it, navController)
            }

            AnimatedVisibility(visible = ScreenConfiguration.ScreensWithoutNavigation.screens.contains(currentRoute).not(), enter = fadeIn(), exit = fadeOut()) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .zIndex(if (isShowingModal) 0f else 1f),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    TopBar(
                        navController = navController,
                        currentRoute = currentRoute,
                        onShowModal
                    )

                    BottomBar(navController = navController, currentRoute = currentRoute)
                }
            }
        }
    }
}
