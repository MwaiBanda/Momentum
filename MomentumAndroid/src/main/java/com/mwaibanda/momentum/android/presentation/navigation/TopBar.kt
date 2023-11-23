package com.mwaibanda.momentum.android.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.mwaibanda.momentum.android.R
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes
import com.mwaibanda.momentum.android.core.utils.ScreenConfiguration

@Composable
fun TopBar(navController: NavController, currentRoute: String?,onShowModal: () -> Unit) {
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
        || currentRoute == NavigationRoutes.MessageDetailScreen.route) Color.Transparent else Color.White,
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
                            tint = if ( currentRoute == NavigationRoutes.MessageDetailScreen.route) Color.White else Color(C.MOMENTUM_ORANGE)
                        )
                        Text(text = "Back", color = if ( currentRoute == NavigationRoutes.MessageDetailScreen.route) Color.White else Color(C.MOMENTUM_ORANGE))
                    }
                }
            if (ScreenConfiguration.ScreensWithoutTopBarIcons.screens.contains(currentRoute).not())
                IconButton(
                    onClick = {
                        onShowModal()
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
            if (ScreenConfiguration.ScreensWithoutTopBarIcons.screens.contains(currentRoute).not())
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
        modifier = Modifier.padding(top = 30.dp),
    )
}