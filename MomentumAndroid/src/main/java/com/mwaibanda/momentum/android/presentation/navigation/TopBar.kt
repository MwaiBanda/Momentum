package com.mwaibanda.momentum.android.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.mwaibanda.momentum.android.R
import com.mwaibanda.momentum.android.core.utils.Constants
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes
import com.mwaibanda.momentum.android.core.utils.ScreenConfiguration

@Composable
fun TopBar(navController: NavController, currentRoute: String?) {
    TopAppBar(
        title = {
            if (ScreenConfiguration.ScreensWithLogo.screens.contains(currentRoute))
                Row(
                    Modifier.fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.momentum_circle),
                        contentDescription = "logo"
                    )
                }

        },
        backgroundColor = if (ScreenConfiguration.ScreensWithoutBackButton.screens.contains(
                currentRoute
            )
        ) Color.Transparent else Color.White,
        elevation = 0.dp,
        navigationIcon = {
            if (ScreenConfiguration.ScreensWithoutBackButton.screens.contains(currentRoute).not())
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Row {
                        Icon(
                            Icons.Filled.ArrowBackIosNew,
                            "",
                            tint = Color(Constants.MOMENTUM_ORANGE)
                        )
                        Text(text = "Back", color = Color(Constants.MOMENTUM_ORANGE))
                    }
                }
            if (ScreenConfiguration.ScreensWithTopBarIcons.screens.contains(currentRoute).not())
                IconButton(
                    onClick = {
                        navController.navigate(NavigationRoutes.TransactionsScreen.route)
                    },
                    enabled = ScreenConfiguration.ScreensWithoutBackButton.screens.contains(
                        currentRoute
                    )
                ) {
                    Icon(
                        Icons.Outlined.AccessTime,
                        contentDescription = "Transactions Icon",
                        tint = Color.White,
                        modifier = Modifier.size(35.dp)
                    )

                }
        },
        actions = {
            if (ScreenConfiguration.ScreensWithTopBarIcons.screens.contains(currentRoute).not())
                IconButton(
                    onClick = { navController.navigate(NavigationRoutes.ProfileScreen.route) },
                    enabled = ScreenConfiguration.ScreensWithoutBackButton.screens.contains(
                        currentRoute
                    )
                ) {
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
}