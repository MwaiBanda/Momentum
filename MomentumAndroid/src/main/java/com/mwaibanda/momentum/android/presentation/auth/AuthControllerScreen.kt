package com.mwaibanda.momentum.android.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mwaibanda.momentum.android.presentation.components.BlurredBackground

@Composable
fun AuthControllerScreen(onCloseModal: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.95f)
    ) {
        BlurredBackground(hasHeaderPadding = false) {
            Column(Modifier.fillMaxHeight()) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Box(
                        Modifier
                            .padding(10.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .size(30.dp)
                    ) {
                        IconButton(onClick = onCloseModal) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close transaction icon",
                                tint = Color.LightGray
                            )
                        }
                    }
                }
                Column(Modifier.fillMaxHeight()) {

                }
            }
        }
    }

}