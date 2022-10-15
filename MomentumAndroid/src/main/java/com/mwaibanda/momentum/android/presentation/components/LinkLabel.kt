package com.mwaibanda.momentum.android.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mwaibanda.momentum.android.core.utils.C

@Composable
fun LinkLabel(title: String, description: String, onClick: () -> Unit) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .clickable { onClick() }
                .padding(10.dp)

        ) {
            Column(Modifier.fillMaxWidth()) {
                Text(text = title, style = MaterialTheme.typography.caption, color = Color.Gray)
                Text(
                    text = description,
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.ExtraBold)
                )
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Icon(imageVector = Icons.Default.Link, contentDescription = "", tint = Color(C.MOMENTUM_ORANGE))
            }
        }

}

@Preview
@Composable
fun LinkLabelPreview() {
    Column(Modifier.background(Color.White)) {
        LinkLabel(title = "Email", description = "Email") {

        }
    }
}