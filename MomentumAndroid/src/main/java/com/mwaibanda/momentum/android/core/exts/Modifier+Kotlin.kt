package com.mwaibanda.momentum.android.core.exts

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

fun Modifier.redacted(isRedacted: Boolean = true): Modifier {
    return this.placeholder(
        visible = isRedacted,
        color = Color.Gray,
        shape = RoundedCornerShape(4.dp),
        highlight = PlaceholderHighlight.shimmer(
            highlightColor = Color.White,
        ),
    )
}
