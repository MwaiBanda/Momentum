package com.mwaibanda.momentum.android.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Person
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
    val bottomTabs = listOf(
        NavigationRoutes.OfferScreen
    )
    val context = LocalContext.current
    Scaffold() {
        Box {
            content(it, navController)
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                TopAppBar(
                    title = {
                        if (ScreensWithLogo.screens.contains(currentRoute))
                            Image(
                                painter = painterResource(id = R.drawable.momentum),
                                contentDescription = "logo"
                            )
                    },
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp,
                    navigationIcon = {
                        if (!ScreensWithoutBackButton.screens.contains(currentRoute))
                            IconButton(onClick = {
                                navController.popBackStack()
                            }) {
                                Row {
                                    Icon(
                                        Icons.Filled.ArrowBackIosNew,
                                        "",
                                        tint = Color(Constants.MomentumOrange)
                                    )
                                    Text(text = "Back", color = Color(Constants.MomentumOrange))
                                }
                            }
                    },
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Outlined.AccountCircle,
                                contentDescription = "Personal Info Icon",
                                tint = Color.White,
                                modifier = Modifier.size(35.dp)
                            )
                        }
                    },
                    modifier = Modifier.padding(
                        rememberInsetsPaddingValues(
                            LocalWindowInsets.current.statusBars,
                            applyBottom = false,
                        )
                    )
                )
                Column {
                    BottomNavigation(
                        elevation = 0.dp,
                        backgroundColor = Color.Transparent
                    ) {
                        bottomTabs.forEach { bottomTab ->
                            BottomNavigationItem(
                                icon = {
                                    Icon(
                                        imageVector = bottomTab.icon ?: Icons.Default.Person,
                                        contentDescription = context.getString(bottomTab.tabName ?: 0)
                                    )
                                },
                                selectedContentColor = Color(Constants.MomentumOrange),
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
                            .windowInsetsBottomHeight(WindowInsets.navigationBars)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}
