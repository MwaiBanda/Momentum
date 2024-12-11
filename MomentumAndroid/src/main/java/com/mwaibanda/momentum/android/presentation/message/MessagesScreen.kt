package com.mwaibanda.momentum.android.presentation.message

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes
import com.mwaibanda.momentum.android.presentation.auth.AuthViewModel
import com.mwaibanda.momentum.android.presentation.components.LoadingSpinner
import com.mwaibanda.momentum.android.presentation.components.MessageCard
import com.mwaibanda.momentum.android.presentation.components.NavigationToolBar
import com.mwaibanda.momentum.domain.models.Message
import com.mwaibanda.momentum.utils.MultiplatformConstants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun MessagesScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    messageViewModel: MessageViewModel,
    onMessageSelected: (Message) -> Unit,
) {

    val messages by messageViewModel.filteredMessages.collectAsState()
    val filteredSeries by messageViewModel.filterBySeries.collectAsState()
    val series by messageViewModel.series.collectAsState()
    val searchTerm by messageViewModel.searchTerm.collectAsState()
    val searchTag by messageViewModel.searchTag().collectAsState(initial = "by event name")
    val refreshScope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }

    fun onRefresh() = refreshScope.launch {
        isRefreshing = true
        delay(1500)
        messageViewModel.getMessages(authViewModel.currentUser?.id ?: "", isRefreshing) {
            isRefreshing = false
        }
    }

    val refreshState = rememberPullRefreshState(isRefreshing, ::onRefresh)

    LaunchedEffect(key1 = Unit, block = {
        messageViewModel.getMessages(authViewModel.currentUser?.id ?: "")
    })

    Box(modifier = Modifier
        .fillMaxSize()
        .pullRefresh(refreshState)) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {

            NavigationToolBar(
                title = "Messages",
                subTitle = MultiplatformConstants.MESSAGES_SUBHEADING,
                searchTerm = searchTerm,
                searchTag = searchTag,
                onSearchTermChanged = messageViewModel::onSearchTermChanged
            ) {
                LazyColumn {
                    items(series) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = filteredSeries == it,
                                onCheckedChange = { isChecked ->
                                   messageViewModel.onFilteredSeriesChanged(if (isChecked) it else "")
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Color(C.MOMENTUM_ORANGE)
                                )
                            )
                            Text(text = it)
                        }
                    }
                }
            }
            Box(contentAlignment = Alignment.TopCenter) {
                LazyColumn(
                    Modifier
                        .padding(horizontal = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (messages.isEmpty() || isRefreshing) {
                        repeat(8) {
                            item {
                                MessageCard(
                                    isRedacted = true,
                                    series = "placeholder",
                                    title = "placeholder",
                                    preacher = "placeholder",
                                    date = "placeholder",
                                    thumbnail = "placeholder"
                                ) {}
                            }

                        }
                    } else {
                        messages.forEachIndexed { i, group ->
                            stickyHeader {
                                Column {
                                    Text(
                                        text = group.series,
                                        style = MaterialTheme.typography.h6,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .background(Color.White)
                                            .padding(bottom = 7.dp)
                                            .padding(vertical = 3.dp)
                                            .padding(top = 4.dp)
                                            .fillMaxWidth()
                                    )
                                }
                            }

                            items(group.messages) { message ->
                                MessageCard(
                                    series = message.series,
                                    title = message.title,
                                    preacher = message.preacher,
                                    date = message.date,
                                    thumbnail = message.thumbnail
                                ) {
                                    onMessageSelected(message)
                                    navController.navigate(NavigationRoutes.MessageDetailScreen.route)
                                }
                            }
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(65.dp))
                    }
                }
                LoadingSpinner(isVisible = messages.isEmpty() && isRefreshing.not())
            }
        }
        PullRefreshIndicator(isRefreshing, refreshState, Modifier.align(Alignment.TopCenter))
    }
}