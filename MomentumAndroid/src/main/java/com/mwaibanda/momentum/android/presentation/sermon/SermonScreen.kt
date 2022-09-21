package com.mwaibanda.momentum.android.presentation.sermon

import android.content.pm.ActivityInfo
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.mwaibanda.momentum.android.presentation.components.LockScreenOrientation
import org.koin.androidx.compose.getViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun SermonScreen(
    navController: NavController,
    sermonViewModel: SermonViewModel = getViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        sermonViewModel.fetchSermons()
    }

    val sermon by sermonViewModel.sermon.collectAsState()
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
                    items(sermon) { item ->
                        Card(
                            Modifier
                                .clickable {
                                    val encodedUrl = URLEncoder.encode(item.videoURL, StandardCharsets.UTF_8.toString())
                                    navController.navigate("play/$encodedUrl")
                                }
                                .weight(0.4f)
                                .defaultMinSize(minHeight = 175.dp),
                            elevation = 2.dp
                        ) {
                            Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Top) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(item.videoThumbnail)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "Sermon thumbnail"
                                )
                                Column(Modifier.padding(8.dp)) {
                                    Text(text = item.series, fontSize = 10.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    Text(text = item.title,  fontSize = 12.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    Text(text = item.preacher, fontSize = 10.sp, color = Color.Gray)
                                    Text(text = item.date, fontSize = 8.sp)
                                }
                            }
                        }
                    }
                    item {
                        Column {
                            Spacer(modifier = Modifier.height(15.dp))
                            BottomSpacing()
                        }
                    }
                }
            }
        }
    }
}