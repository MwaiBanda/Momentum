package com.mwaibanda.momentum.android.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mwaibanda.momentum.android.core.utils.Constants

@Composable
fun TranslucentBackground(modifier: Modifier, content: @Composable () -> Unit) {
    Box(modifier) {
        Box(
            Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(15.dp))
                .background(Color.Black.copy(0.2f))
                .blur(radius = 200.dp)
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(0.1f))
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color.White.copy(alpha = 0.08f))
                        .blur(radius = 10.dp)
                ) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .border(
                                width = 3.dp, brush = Brush.linearGradient(
                                    listOf(
                                        Color(Constants.MOMENTUM_ORANGE).copy(alpha = 0.5f),
                                        Color(Constants.MOMENTUM_ORANGE).copy(alpha = 0.3f),
                                        Color.White.copy(alpha = 0.2f),
                                        Color.Transparent,
                                        Color.White.copy(alpha = 0.2f)
                                    ),
                                ), shape = RoundedCornerShape(15.dp)
                            )
                            .padding(2.dp)
                    ) {
                        Column {
                            content()
                        }
                    }
                }
            }
        }

    }
}

@Preview
@Composable
fun TranslucentBackgroundPreview() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TranslucentBackground(Modifier.size(300.dp)) {

        }
    }
}
