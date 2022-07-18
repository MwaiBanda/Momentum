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
        backgroundColor = Color.Transparent,
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
                            tint = Color(Constants.MomentumOrange)
                        )
                        Text(text = "Back", color = Color(Constants.MomentumOrange))
                    }
                }
            else
                IconButton(onClick = {

                }) {
                    Icon(
                        Icons.Outlined.AccessTime,
                        contentDescription = "History Icon",
                        tint = Color.White,
                        modifier = Modifier.size(35.dp)
                    )

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
}