package com.mwaibanda.momentum.android.presentation.sermon

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mwaibanda.momentum.android.core.utils.Constants
import com.mwaibanda.momentum.android.presentation.components.BottomSpacing
import com.mwaibanda.momentum.android.presentation.components.SermonCard
import com.mwaibanda.momentum.domain.models.Sermon
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun SermonScreen(
    navController: NavController,
    sermonViewModel: SermonViewModel
) {
    LaunchedEffect(key1 = Unit) {
        sermonViewModel.getWatchSermons()
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
                Box(contentAlignment = Alignment.TopCenter) {
                    Column {


                        LazyVerticalGrid(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(15.dp),
                            horizontalArrangement = Arrangement.spacedBy(15.dp)
                        ) {

                            if (sermons.isEmpty()) {
                                items(MutableList(12) {
                                    Sermon(
                                        id = "10001",
                                        series = "Pre-Decide: Better Choices, Better Life",
                                        title = "The Quality of Your Decisions Determines the Quality of Your Life",
                                        preacher = "Charlie Arms",
                                        videoThumbnail = "https://6a0037bf541aecc8a1de-c14fd83124cd2a87055253bd0f7faf70.ssl.cf2.rackcdn.com/video-thumb/1/0e14909937_1664818532_10-02-22-worship-servicetrim.jpg",
                                        videoURL = "",
                                        date = "Oct 2, 2022"
                                    )
                                }
                                ) {
                                    SermonCard(
                                        isPlaceholder = true,
                                        sermon = it,
                                        watchedSermons = sermonViewModel.watchedSermons,
                                        modifier = Modifier
                                            .weight(0.4f)
                                            .defaultMinSize(minHeight = 175.dp)
                                    )
                                }
                            } else {
                                items(sermons) { sermon ->
                                    SermonCard(
                                        sermon = sermon,
                                        watchedSermons = sermonViewModel.watchedSermons,
                                        modifier = Modifier
                                            .clickable {
                                                val encodedUrl = URLEncoder.encode(
                                                    sermon.videoURL,
                                                    StandardCharsets.UTF_8.toString()
                                                )
                                                navController.navigate("play/$encodedUrl")
                                                sermonViewModel.currentSermon = sermon
                                            }
                                            .weight(0.4f)
                                            .defaultMinSize(minHeight = 175.dp)
                                    )
                                }
                            }

                            item {
                                if (sermons.isNotEmpty() && canLoadMoreSermons) {
                                    val offset = LocalConfiguration.current.screenWidthDp * 0.25
                                    Button(
                                        modifier = Modifier.offset(x = offset.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = Color(
                                                Constants.MOMENTUM_ORANGE
                                            )
                                        ),
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
                    Column {
                        AnimatedVisibility(visible = sermons.isEmpty()) {
                            Column(Modifier.size(30.dp)) {
                                Surface(
                                    elevation = 4.dp,
                                    shape = CircleShape
                                ) {
                                    Box(
                                        Modifier
                                            .padding(5.dp)
                                            .size(25.dp)
                                            .background(
                                                color = Color.White,
                                                shape = CircleShape
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(
                                            color = Color.Black,
                                            modifier = Modifier
                                                .size(25.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}