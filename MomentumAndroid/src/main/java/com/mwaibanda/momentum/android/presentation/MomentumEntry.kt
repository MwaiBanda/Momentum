package com.mwaibanda.momentum.android.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mwaibanda.momentum.android.R
import com.mwaibanda.momentum.android.core.utils.Constants
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes
import com.mwaibanda.momentum.android.core.utils.ScreenConfiguration.*
import com.mwaibanda.momentum.android.presentation.navigation.BottomBar
import com.mwaibanda.momentum.android.presentation.navigation.TopBar


@Composable
fun MomentumEntry(content: @Composable (PaddingValues, NavHostController) -> Unit) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    val navController = rememberNavController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )

    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold() {
        Box {
            content(it, navController)
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
