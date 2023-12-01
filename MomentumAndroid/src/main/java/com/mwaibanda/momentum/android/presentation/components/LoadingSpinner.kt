package com.mwaibanda.momentum.android.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingSpinner(isVisible: Boolean) {
    Column {
        AnimatedVisibility(visible = isVisible) {
            Column(Modifier.size(30.dp)) {
                Surface(
                    elevation = 4.dp,
                    shape = CircleShape
                ) {
                    Box(
                        Modifier
                            .padding(5.dp)
                            .size(25.dp)
                            .background(
                                color = Color.White,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color.Black,
                            modifier = Modifier
                                .size(25.dp)
                        )
                    }
                }
            }
        }
    }
}