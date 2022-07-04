package com.mwaibanda.momentum.android.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mwaibanda.momentum.android.R


@Composable
fun MomentumEntry(content: @Composable (PaddingValues) -> Unit) {
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
                        Image(imageVector = Icons.Outlined.AccountCircle, contentDescription = "Personal Info Icon")
                    }
                }
            )
        }
    }
}