package com.mwaibanda.momentum.android.presentation.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BaseModal(
    closeModal: () -> Unit,
    title: String = "",
    arrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable () -> Unit
) {
    BackHandler {
        closeModal()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.95f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = arrangement
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(10.dp)
                    .padding(bottom = 8.dp)
            )
            IconButton(onClick = { closeModal() }, Modifier.padding(horizontal = 10.dp)) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close  icon")
            }
        }
        content()
    }
}