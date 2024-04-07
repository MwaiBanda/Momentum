package com.mwaibanda.momentum.android.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.data.db.MomentumSermon
import com.mwaibanda.momentum.data.db.SermonFavourite
import com.mwaibanda.momentum.domain.models.Sermon

@Composable
fun SermonCard(
    isRedacted: Boolean = false,
    sermon: Sermon,
    watchedSermons: List<MomentumSermon> = emptyList(),
    favouriteSermons: List<SermonFavourite> = emptyList(),
    searchTerm: String = "",
    filterOldest: Boolean = false,
    filterNewest: Boolean = false,
    modifier: Modifier = Modifier,
    onFavouriteChange: (Boolean) -> Unit = {}
) {
    var isFavourite by remember {
        mutableStateOf(false)
    }
    var thumbnailHasLoaded by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = favouriteSermons, key2 = filterOldest, key3 = filterNewest) {
        favouriteSermons
            .firstOrNull {
                it.id == sermon.id
            }?.let {
                isFavourite = true
            } ?: kotlin.run {
                isFavourite = false
        }
    }

    LaunchedEffect(key1 = searchTerm) {
        favouriteSermons
            .firstOrNull {
                it.id == sermon.id
            }?.let {
                isFavourite = true
            } ?: kotlin.run {
            isFavourite = false
        }
    }
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            verticalArrangement = Arrangement.Top
        ) {
            Box (Modifier.fillMaxWidth()){

                Image(
                    painter = painterResource(id = R.drawable.thumbnail),
                    contentDescription = "Video thumbnail placeholder",
                    modifier = Modifier.fillMaxWidth().redacted(true)
                )
                if (isRedacted.not()) {
                    Box(
                        contentAlignment = Alignment.TopEnd,
                        modifier = Modifier.fillMaxWidth().redacted(isRedacted),
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(sermon.videoThumbnail)
                                .crossfade(true)
                                .build(),
                            modifier = Modifier.fillMaxWidth(),
                            contentDescription = "Sermon thumbnail",
                            contentScale = ContentScale.FillWidth,
                            onSuccess = { thumbnailHasLoaded = true},
                        )
                        if (thumbnailHasLoaded) {
                            IconButton(
                                onClick = {
                                    isFavourite = isFavourite.not()
                                    onFavouriteChange(isFavourite)
                                },
                                modifier = Modifier.offset(y = -(7).dp, x = 5.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Box(
                                        modifier = Modifier
                                            .size(26.dp)
                                            .clip(CircleShape)
                                            .background(Color.LightGray.copy(0.75f))
                                    )

                                    Icon(
                                        painter = painterResource(id = R.drawable.cards_heart),
                                        contentDescription = "",
                                        tint = if (isFavourite) Color.Red else Color.DarkGray,
                                        modifier = Modifier.size(15.dp)
                                    )


                                }
                            }
                        }
                    }
                }
            }
            watchedSermons
                .firstOrNull {
                    it.id == sermon.id
                }?.let {
                    LinearProgressIndicator(
                        progress = it.last_played_percentage.toFloat() / 100.0f,
                        color = Color(C.MOMENTUM_ORANGE),
                        modifier = Modifier.height(4.dp)
                    )
                } ?: kotlin.run {
                LinearProgressIndicator(
                    progress = 0f,
                    color = Color.Gray,
                    modifier = Modifier.height(4.dp)
                )
            }
            Column(Modifier.padding(8.dp)) {

                Text(
                    text = sermon.series,
                    fontSize = 10.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(bottom = if (isRedacted) 2.dp else 0.5.dp)
                        .redacted(isRedacted)
                )
                Text(
                    text = sermon.title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.redacted(isRedacted)
                )
                Text(
                    text = sermon.preacher,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(vertical = if (isRedacted) 2.dp else 0.5.dp)
                        .redacted(isRedacted)
                )
                Text(
                    text = sermon.date,
                    fontSize = 8.sp,
                    modifier = Modifier.redacted(isRedacted)
                )
            }
        }
    }

}