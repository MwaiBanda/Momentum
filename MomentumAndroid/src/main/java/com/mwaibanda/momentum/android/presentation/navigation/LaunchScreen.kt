package com.mwaibanda.momentum.android.presentation.navigation

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mwaibanda.momentum.android.R
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes
import kotlinx.coroutines.delay

@Composable
fun LaunchScreen(navController: NavController) {
    val scale = remember {
        Animatable(0.5f)
    }
    LaunchedEffect(key1 = Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                500, easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(2200L)
        navController.navigate(NavigationRoutes.OfferScreen.route)


    }
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.momentum_long),
                contentDescription = "logo",
                modifier = Modifier
                    .padding(20.dp)
                    .scale(scale.value)
            )
        }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "Copyright © 2022 Momentum. All rights reserved.", modifier = Modifier.padding(bottom = 15.dp))
            Spacer(
                Modifier
                    .windowInsetsBottomHeight(WindowInsets.navigationBars)
                    .fillMaxWidth())
        }
    }

}