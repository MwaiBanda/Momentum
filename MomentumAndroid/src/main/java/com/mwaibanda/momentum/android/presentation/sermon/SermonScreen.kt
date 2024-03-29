package com.mwaibanda.momentum.android.presentation.sermon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.android.presentation.components.BottomSpacing
import com.mwaibanda.momentum.android.presentation.components.LoadingSpinner
import com.mwaibanda.momentum.android.presentation.components.NavigationToolBar
import com.mwaibanda.momentum.android.presentation.components.SermonCard
import com.mwaibanda.momentum.domain.models.Sermon

@Composable
fun SermonScreen(
    navController: NavController,
    sermonViewModel: SermonViewModel,
    onSermonClicked: (Sermon) -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        sermonViewModel.fetchSermons()
        sermonViewModel.getFavouriteSermons()
        sermonViewModel.getWatchSermons()
    }

    val sermons by sermonViewModel.filteredSermons.collectAsState()
    val watchedSermons by sermonViewModel.watchedSermons.collectAsState()
    val favouriteSermons by sermonViewModel.favouriteSermons.collectAsState()
    val searchTerm by sermonViewModel.searchTerm.collectAsState()
    val searchTag by sermonViewModel.searchTag().collectAsState(initial = "by sermon")
    val canLoadMoreSermons by sermonViewModel.canLoadMoreSermons.collectAsState()

    val filterFavourites by sermonViewModel.filterFavourites.collectAsState()
    val filterOldest by sermonViewModel.filterOldest.collectAsState()
    val filterNewest by sermonViewModel.filterNewest.collectAsState()

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        NavigationToolBar(
            title = "Sermons",
            subTitle = "Tap to watch recent sermons",
            searchTerm = searchTerm,
            searchTag = searchTag,
            onSearchTermChanged = sermonViewModel::onSearchTermChanged
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = filterFavourites,
                    onCheckedChange = sermonViewModel::setFilterFavourites,
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(C.MOMENTUM_ORANGE)
                    )
                )
                Text(text = "Favourites")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = filterNewest,
                    onCheckedChange = sermonViewModel::setFilterNewest,
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(C.MOMENTUM_ORANGE)
                    )
                )
                Text(text = "Newest")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = filterOldest,
                    onCheckedChange = sermonViewModel::setFilterOldest,
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(C.MOMENTUM_ORANGE)
                    )
                )
                Text(text = "Oldest")
            }
        }


        Spacer(modifier = Modifier.height(10.dp))
        Box(contentAlignment = Alignment.TopCenter) {
            Column {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp),
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {

                    if (sermons.isEmpty() && searchTerm.isEmpty()) {
                        items(MutableList(12) {
                            Sermon(
                                id = "10001",
                                series = "Pre-Decide: Better Choices, Better Life",
                                title = "The Quality of Your Decisions Determines the Quality of Your Life",
                                preacher = "Charlie Arms",
                                videoThumbnail = "thumbnail",
                                videoURL = "",
                                date = "Oct 2, 2022",
                                dateMillis = 0L
                            )
                        }
                        ) {
                            SermonCard(
                                isRedacted = true,
                                sermon = it,
                                modifier = Modifier
                                    .weight(0.4f)
                                    .defaultMinSize(minHeight = 175.dp)
                            )
                        }
                    } else {
                        items(sermons) { sermon ->
                            SermonCard(
                                sermon = sermon,
                                watchedSermons = watchedSermons,
                                favouriteSermons = favouriteSermons,
                                filterOldest = filterOldest,
                                filterNewest = filterNewest,
                                searchTerm = searchTerm,
                                modifier = Modifier
                                    .clickable {
                                        onSermonClicked(sermon)
                                        sermonViewModel.currentSermon = sermon
                                    }
                                    .weight(0.4f)
                                    .defaultMinSize(minHeight = 175.dp)
                            ) { isFavourite ->
                                if (isFavourite) {
                                    sermonViewModel.addFavourite(id = sermon.id)
                                } else {
                                    sermonViewModel.removeFavourite(id = sermon.id)
                                }
                            }
                        }
                    }


                    item {
                        if (sermons.isNotEmpty() && canLoadMoreSermons && searchTerm.isEmpty()) {
                            val offset = LocalConfiguration.current.screenWidthDp * 0.25
                            Button(
                                modifier = Modifier.offset(x = offset.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(
                                        C.MOMENTUM_ORANGE
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
            LoadingSpinner(isVisible = sermons.isEmpty() && searchTerm.isEmpty())
        }
    }
}


