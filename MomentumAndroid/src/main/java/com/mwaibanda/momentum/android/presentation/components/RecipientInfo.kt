package com.mwaibanda.momentum.android.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun RecipientInfo(
    title: String,
    description: String,
    icon: ImageVector,
) {
    Row {
        Icon(imageVector = icon, contentDescription = "")
        Column(Modifier.padding(start = 10.dp, bottom = 10.dp)) {
            Text(text = title, fontWeight = FontWeight.Medium)
            Text(text = description)
        }
    }
}