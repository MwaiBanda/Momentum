package com.mwaibanda.momentum.android.presentation.messages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mwaibanda.momentum.android.core.utils.C

@Composable
fun MessagesScreen() {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        Column {
            Column {
                Spacer(modifier = Modifier.height(65.dp))
                Text(
                    text = "Messages",
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(10.dp)
                )

                Divider()
                Text(
                    text = "View upcoming messages and add notes".uppercase(),
                    modifier = Modifier.padding(horizontal = 10.dp),
                    style = MaterialTheme.typography.caption,
                    color = Color(C.MOMENTUM_ORANGE)
                )
            }
        }
    }
}