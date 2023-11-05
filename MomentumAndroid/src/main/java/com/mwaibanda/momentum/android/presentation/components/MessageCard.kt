package com.mwaibanda.momentum.android.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun MessageCard(onMessageSelected: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().heightIn(max = 150.dp).clip(RoundedCornerShape(8.dp)).clickable{
            onMessageSelected()
        }.padding(2.dp),
        elevation = 2.dp
    ) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://github.com/MwaiBanda/Momentum/assets/49708426/e5b6e89c-57bd-4c47-9eb0-d3a05a4f97fe")
                    .crossfade(true)
                    .build(),
                contentDescription = "Sermon thumbnail",
                contentScale = ContentScale.Fit,
                modifier = Modifier.heightIn(max = 150.dp).widthIn(max = 150.dp)
            )
            Column(Modifier.padding(8.dp)) {

                Text(
                    text = "Don't be a Pharisee",
                    fontSize = 10.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(bottom = 0.5.dp)

                )
                Text(
                    text = "Don't be a Pharisee: Part 1",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,

                    )
                Text(
                    text = "Charlie Arms",
                    fontSize = 10.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 0.5.dp)
                )
                Text(
                    text = "Nov 5, 2023",
                    fontSize = 8.sp,
                )
            }
        }
    }
}