package com.mwaibanda.momentum.android.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mwaibanda.momentum.android.R


@Composable
fun MomentumEntry(content: @Composable (PaddingValues) -> Unit) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color(0xFFE55F1F).copy(0.5f),
            darkIcons = useDarkIcons
        )

    }
    Scaffold() {
        Box {
            content(it)
            TopAppBar(
                title = {
                    Image(painter = painterResource(id = R.drawable.momentum), contentDescription = "logo")
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Outlined.AccountCircle, contentDescription = "Personal Info Icon",
                        tint = Color.White, modifier = Modifier.size(35.dp))
                    }
                }
            )
        }
    }
}