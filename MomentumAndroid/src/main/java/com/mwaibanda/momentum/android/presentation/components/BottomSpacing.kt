package com.mwaibanda.momentum.android.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomSpacing() {
    Spacer(Modifier.height(60.dp))
    Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars).fillMaxWidth())
}