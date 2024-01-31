@file:OptIn(ExperimentalFoundationApi::class)

package com.mwaibanda.momentum.android.presentation.event

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mwaibanda.momentum.android.core.exts.redacted
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.android.presentation.components.LoadingSpinner
import com.mwaibanda.momentum.domain.models.Event
import com.mwaibanda.momentum.domain.models.GroupedEvent
import com.mwaibanda.momentum.utils.MultiplatformConstants
import org.koin.androidx.compose.getViewModel

@Composable
fun EventScreen(eventViewModel: EventViewModel = getViewModel()){
    var groupedEvents by remember {
        mutableStateOf(emptyList<GroupedEvent>())
    }
    LaunchedEffect(key1 = Unit, block = {
        eventViewModel.getEvents {
            groupedEvents = it
        }
    })
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        Column {
            Column {
                Spacer(modifier = Modifier.height(25.dp))

                Text(
                    text = "Events",
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(10.dp)
                )

                Divider()
                Text(
                    text = MultiplatformConstants.EVENTS_SUBHEADING.uppercase(),
                    modifier = Modifier.padding(horizontal = 10.dp).padding(bottom = 2.dp),
                    style = MaterialTheme.typography.caption,
                    color = Color(C.MOMENTUM_ORANGE)
                )
                Box(contentAlignment = Alignment.TopCenter) {

                    LazyColumn(
                        Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding()
                            .padding(horizontal = 10.dp)
                    ) {
                        if (groupedEvents.isEmpty()) {
                            item {
                                repeat(2) {
                                    GroupedEvents(
                                        isRedacted = true,
                                        group = GroupedEvent(
                                            monthAndYear = "placeholder",
                                            events = MutableList(3) {
                                                Event(
                                                    id = "placeholder",
                                                    startTime = "placeholder",
                                                    endTime = "placeholder",
                                                    description = "placeholder",
                                                    intervalSummary = "placeholder",
                                                    name = "placeholder",
                                                    thumbnail = "placeholder"
                                                )
                                            }
                                        )
                                    )
                                }
                            }
                        } else {
                            groupedEvents.groupBy { it.monthAndYear }.forEach { (initial, groupedEvents) ->
                                stickyHeader {
                                    Text(
                                        text = initial,
                                        style = MaterialTheme.typography.h6,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .background(Color.White)
                                            .padding(bottom = 5.dp)
                                            .fillMaxWidth()

                                    )
                                }

                                items(groupedEvents) { group ->
                                    GroupedEvents(group = group)
                                }
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(65.dp))
                        }
                    }
                    LoadingSpinner(isVisible = groupedEvents.isEmpty())
                }

            }
        }
    }
}

@Composable
fun GroupedEvents(isRedacted: Boolean = false, group: GroupedEvent) {
    Column {
        group.events.forEach { event ->
            EventCard(isRedacted = isRedacted, event = event) {

            }
        }
    }

    Spacer(modifier = Modifier.height(15.dp))
}
@Composable
fun EventCard(isRedacted: Boolean, event: Event, onCardClicked: () -> Unit) {
    Card(
        Modifier
            .heightIn(min = 55.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .padding(top = 10.dp)
            .padding(1.dp)
            .clickable(enabled = false) { onCardClicked() }
    ) {
        Column(Modifier.padding(6.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                    Row(
                        Modifier.fillMaxWidth(0.95f),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = event.getFormattedStartDate(), fontWeight = FontWeight.Medium,
                                    modifier = Modifier.redacted(isRedacted)
                            )
                            Text(
                                text = event.name,
                                style = MaterialTheme.typography.body1,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(C.MOMENTUM_ORANGE),
                                modifier = Modifier
                                    .padding(vertical = if (isRedacted) 2.dp else 0.dp)
                                    .redacted(isRedacted)
                            )
                            Text(text = event.intervalSummary, color = Color.Gray, style = MaterialTheme.typography.caption,
                                modifier = Modifier.redacted(isRedacted)
                            )
                        }
                        Text(
                            text = event.getDisplayEventTime(),
                            color = Color.Gray,
                            modifier = Modifier
                                .padding(vertical = if (isRedacted) 2.dp else 0.dp)
                                .redacted(isRedacted)
                        )
                    }
                /*
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Profile navigation icon",
                    tint = Color(0xFF434359)
                )
                */
            }
        }
    }
}