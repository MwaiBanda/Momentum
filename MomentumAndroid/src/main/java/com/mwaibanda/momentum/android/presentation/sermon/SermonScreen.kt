package com.mwaibanda.momentum.android.presentation.sermon

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mwaibanda.momentum.android.core.utils.Constants
import com.mwaibanda.momentum.android.presentation.components.BottomSpacing
import org.koin.androidx.compose.getViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun SermonScreen(
    navController: NavController,
    sermonViewModel: SermonViewModel
) {
    LaunchedEffect(key1 = Unit) {
        sermonViewModel.fetchSermons()
    }

    val sermons by sermonViewModel.sermon.collectAsState()
    val canLoadMoreSermons by sermonViewModel.canLoadMoreSermons.collectAsState()

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        Column {
            Column {
                Spacer(modifier = Modifier.height(65.dp))
                Text(
                    text = "Sermons",
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(10.dp)
                )
                Divider()
                Text(
                    text = "Tap to watch recent sermons".uppercase(),
                    modifier = Modifier.padding(horizontal = 10.dp),
                    style = MaterialTheme.typography.caption,
                    color = Color(Constants.MOMENTUM_ORANGE)
                )
                Spacer(modifier = Modifier.height(10.dp))
                    LazyVerticalGrid(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        items(sermons) { sermon ->
                            Card(
                                Modifier
                                    .clickable {
                                        val encodedUrl = URLEncoder.encode(
                                            sermon.videoURL,
                                            StandardCharsets.UTF_8.toString()
                                        )
                                        navController.navigate("play/$encodedUrl")
                                        sermonViewModel.currentSermon = sermon
                                    }
                                    .weight(0.4f)
                                    .defaultMinSize(minHeight = 175.dp),
                                elevation = 2.dp
                            ) {
                                Column(
                                    Modifier.fillMaxHeight(),
                                    verticalArrangement = Arrangement.Top
                                ) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(sermon.videoThumbnail)
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = "Sermon thumbnail",

                                        )
                                    Column(Modifier.padding(8.dp)) {
                                        Text(
                                            text = sermon.series,
                                            fontSize = 10.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = sermon.title,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = sermon.preacher,
                                            fontSize = 10.sp,
                                            color = Color.Gray
                                        )
                                        Text(text = sermon.date, fontSize = 8.sp)
                                    }
                                }
                            }
                        }

                        item {
                            if (sermons.isNotEmpty() && canLoadMoreSermons) {
                                val offset = LocalConfiguration.current.screenWidthDp * 0.25
                                Button(
                                    modifier = Modifier.offset(x = offset.dp),
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(Constants.MOMENTUM_ORANGE)),
                                    onClick = { sermonViewModel.loadMoreSermons() }) {
                                    Text(text = "Load More", color = Color.White)
                                }
                            }
                        }
                        item {
                            Column {
                                Spacer(modifier = Modifier.height(if (canLoadMoreSermons) 55.dp else 20.dp))
                                BottomSpacing()
                            }
                        }
                    }

            }
        }
    }
}