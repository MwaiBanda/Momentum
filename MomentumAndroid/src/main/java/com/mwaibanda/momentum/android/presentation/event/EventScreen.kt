package com.mwaibanda.momentum.android.presentation.event

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
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
import com.mwaibanda.momentum.android.core.utils.C
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
                Spacer(modifier = Modifier.height(65.dp))

                Text(
                    text = "Events",
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(10.dp)
                )

                Divider()
                Text(
                    text = MultiplatformConstants.EVENTS_SUBHEADING.uppercase(),
                    modifier = Modifier.padding(horizontal = 10.dp),
                    style = MaterialTheme.typography.caption,
                    color = Color(C.MOMENTUM_ORANGE)
                )
                LazyColumn(
                    Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .systemBarsPadding()
                        .padding(horizontal = 10.dp)
                ) {
                    items(groupedEvents) { group ->
                        Text(text = group.monthAndYear, style = MaterialTheme.typography.h6, fontWeight = FontWeight.Bold)
                        group.events.forEach { event ->
                            Spacer(modifier = Modifier.height(10.dp))
                            EventCard(event = event) {
                                
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                    item {
                        Spacer(modifier = Modifier.height(65.dp))
                    }
                }

            }
        }
    }
}


@Composable
fun EventCard(event: Event, onCardClicked: () -> Unit) {
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
                            Text(text = event.getFormattedStartDate(), fontWeight = FontWeight.Medium)
                            Text(text = event.name, style = MaterialTheme.typography.body1, fontWeight = FontWeight.ExtraBold, color = Color(C.MOMENTUM_ORANGE))
                            Text(text = event.intervalSummary, color = Color.Gray, style = MaterialTheme.typography.caption)
                        }
                        Text(text = event.getDisplayEventTime(), color = Color.Gray)
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