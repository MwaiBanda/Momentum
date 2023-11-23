package com.mwaibanda.momentum.android.presentation.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mwaibanda.momentum.domain.models.GroupedEvent
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

    LazyColumn(
        Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .systemBarsPadding()
    ) {
        items(groupedEvents) { group ->
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = group.monthAndYear)
            group.events.forEach { event ->
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = event.getFormattedStartDate())
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = event.name)
                    Text(text = "${event.getFormattedStartTime()}-${event.getFormattedEndTime()}")

                }
            }

        }
    }
}