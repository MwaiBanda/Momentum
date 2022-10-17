package com.mwaibanda.momentum.android.presentation.sermon

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.android.presentation.components.*
import com.mwaibanda.momentum.domain.models.Sermon
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun SermonScreen(
    navController: NavController,
    sermonViewModel: SermonViewModel
) {
    LaunchedEffect(key1 = Unit) {
        sermonViewModel.fetchSermons()
        sermonViewModel.getFavouriteSermons()
        sermonViewModel.getWatchSermons {
        }

    }

    val sermons by sermonViewModel.filteredSermons.collectAsState()
    val watchedSermons by sermonViewModel.watchedSermons.collectAsState()
    val favouriteSermons by sermonViewModel.favouriteSermons.collectAsState()
    val searchTerm by sermonViewModel.searchTerm.collectAsState()
    val canLoadMoreSermons by sermonViewModel.canLoadMoreSermons.collectAsState()

    val filterFavourites by sermonViewModel.filterFavourites.collectAsState()
    val filterOldest by sermonViewModel.filterOldest.collectAsState()
    val filterNewest by sermonViewModel.filterNewest.collectAsState()

    var showSearchBar by remember { mutableStateOf(false) }
    val searchOptionsHeight by animateDpAsState(targetValue = if (showSearchBar) 57.dp else 0.dp)
    var showFilterBar by remember { mutableStateOf(false) }
    val filterOptionsHeight by animateDpAsState(targetValue = if (showFilterBar) 145.dp else 0.dp)
    val optionsHeight by animateDpAsState(targetValue = if (showFilterBar) filterOptionsHeight else searchOptionsHeight)

    val focusManager = LocalFocusManager.current

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        Column {
            Column {
                Spacer(modifier = Modifier.height(65.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Sermons",
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(10.dp)
                    )
                    Row {
                        IconButton(
                            onClick = {
                                showSearchBar = showSearchBar.not()
                                showFilterBar = false
                            },
                            modifier = Modifier.offset(x = 16.dp)
                        ) {
                            if (showSearchBar) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Search Close"
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search Icon"
                                )
                            }
                        }
                        IconButton(onClick = {
                            showFilterBar = showFilterBar.not()
                            showSearchBar = false
                        }) {
                            if (showFilterBar) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Filter Close"
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Filter Icon"
                                )
                            }
                        }
                    }
                }
                Divider()
                if (showSearchBar || showFilterBar) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .height(optionsHeight)
                    ) {
                        if (showSearchBar) {
                            TextField(
                                value = searchTerm,
                                onValueChange = sermonViewModel::onSearchTermChanged,
                                label = { Text(text = "Search for sermons") },
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.textFieldColors(
                                    cursorColor = Color(C.MOMENTUM_ORANGE),
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    placeholderColor = Color(C.MOMENTUM_ORANGE),
                                    backgroundColor = Color.White,
                                    focusedLabelColor = Color(C.MOMENTUM_ORANGE),

                                    ),
                                keyboardOptions = KeyboardOptions(
                                    capitalization = KeyboardCapitalization.Words,
                                    imeAction = ImeAction.Done,
                                ),
                                keyboardActions = KeyboardActions(onDone = {
                                    focusManager.clearFocus()
                                })
                            )
                        } else {
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
                        Divider()
                    }
                } else {
                    Text(
                        text = "Tap to watch recent sermons".uppercase(),
                        modifier = Modifier.padding(horizontal = 10.dp),
                        style = MaterialTheme.typography.caption,
                        color = Color(C.MOMENTUM_ORANGE)
                    )
                }


                Spacer(modifier = Modifier.height(10.dp))
                Box(contentAlignment = Alignment.TopCenter) {
                    Column {


                        LazyVerticalGrid(
                            modifier = Modifier.padding(horizontal = 10.dp),
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
                                        isPlaceholder = true,
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
                                                val encodedUrl = URLEncoder.encode(
                                                    sermon.videoURL,
                                                    StandardCharsets.UTF_8.toString()
                                                )
                                                navController.navigate("play/$encodedUrl")
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
                    Column {
                        AnimatedVisibility(visible = sermons.isEmpty() && searchTerm.isEmpty()) {
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