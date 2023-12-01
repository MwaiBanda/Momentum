package com.mwaibanda.momentum.android.presentation.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mwaibanda.momentum.android.R
import com.mwaibanda.momentum.android.core.exts.redacted

@Composable
fun MessageCard(
    isRedacted: Boolean = false,
    series: String,
    title: String,
    preacher: String,
    date: String,
    thumbnail: String,
    onMessageSelected: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().heightIn(max = 150.dp).clip(RoundedCornerShape(8.dp)).clickable{
            onMessageSelected()
        }.padding(2.dp),
        elevation = 2.dp
    ) {
        Row {
            if (isRedacted) {
                Image(
                    painter = painterResource(id = R.drawable.thumbnail),
                    contentDescription = "Message thumbnail placeholder",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.heightIn(max = 150.dp).widthIn(max = 150.dp).redacted(true)
                )
            } else {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(thumbnail)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Message thumbnail",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.heightIn(max = 150.dp).widthIn(max = 150.dp)
                )
            }
            Column(Modifier.padding(8.dp)) {

                Text(
                    text = series,
                    fontSize = 10.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(bottom = if (isRedacted) 2.dp else 0.5.dp)
                        .redacted(isRedacted)

                )
                Text(
                    text = title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .redacted(isRedacted)
                    )
                Text(
                    text = preacher,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(vertical = if (isRedacted) 2.dp else 0.5.dp)
                        .redacted(isRedacted)
                )
                Text(
                    text = date,
                    fontSize = 8.sp,
                    modifier = Modifier.redacted(isRedacted)
                )
            }
        }
    }
}